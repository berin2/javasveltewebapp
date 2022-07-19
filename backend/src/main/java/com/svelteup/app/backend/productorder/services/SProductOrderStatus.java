package com.svelteup.app.backend.productorder.services;

import com.svelteup.app.backend.aop.aspects.paireduser.OwningUserPairedNonPkEntityAccessCheck;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.notificationmessage.events.NotificationEvent;
import com.svelteup.app.backend.productorder.dto.PutProductOrderStatusDto;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.repositories.RProductOrder;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
@Data @EqualsAndHashCode(callSuper = false)
public class SProductOrderStatus extends SSurrogateEntity<Long,ProductOrder> implements ApplicationEventPublisherAware, OwningUserPairedNonPkEntityAccessCheck<ProductOrder> {
    private static final String STATUS_ERROR_STRING = "Buyer user %s does not have permission to change from status %d to status %d";
    private static final String USER_NOT_BUYER_OR_SELLER_ERROR = "User is not %s is not a seller or buyer for the product order with surrogate id %s";
    private static final String ORDER_STATUS_NOT_SUPPORTED = "Order status change to enum name %s is not supported by the current application.";

    private RProductOrder productOrderRepository;
    private SHttpExceptionThrower exceptionThrower;
    private ApplicationEventPublisher applicationEventPublisher;

    public SProductOrderStatus(RProductOrder productOrderRepository) throws NotSupportedException {
        super(productOrderRepository);
    }

    public void put(String authenticatedUser, PutProductOrderStatusDto putProductOrderStatusDto) throws NotSupportedException, OperationNotSupportedException {
        ApplicationNotificationEnums notificationEnum  =  putProductOrderStatusDto.productOrderStatusCode;
        UUID productOrderId = putProductOrderStatusDto.productOrderId;
        ProductOrder discoveredOrder = this.findBySurrogateId(productOrderId);

        this.canPerformStatusChange(authenticatedUser,notificationEnum,discoveredOrder);

        discoveredOrder.setProductOrderStatus(notificationEnum);
        this.publishOrderStatusChangeEvent(notificationEnum,discoveredOrder);
        productOrderRepository.save(discoveredOrder);
    }

    public void canPerformStatusChange(String authenticatedUser, ApplicationNotificationEnums statusCode, ProductOrder statusChangeOrder) throws AccessDeniedException
    {
        String buyerUsername = statusChangeOrder.getOwningUsername();
        String sellerUsername = statusChangeOrder.getSecondaryOwningUsername();
        ApplicationNotificationEnums oldStatusCode = statusChangeOrder.getProductOrderStatus();

        if(authenticatedUser.equals(buyerUsername))
            this.performBuyerChecks(authenticatedUser,statusCode,oldStatusCode);
        else if(authenticatedUser.equals(sellerUsername))
            this.performSellerChecks(authenticatedUser,statusCode,oldStatusCode);
        else
            throw new AccessDeniedException(String.format(USER_NOT_BUYER_OR_SELLER_ERROR, authenticatedUser,statusChangeOrder.getSurrogateId()));
    }
    private void performBuyerChecks(String buyerUsername, ApplicationNotificationEnums newStatusCode, ApplicationNotificationEnums oldStatusCode) throws AccessDeniedException
    {
        //Seller can request to take a placed but not finished and delivered order to cancel state. All other status changes
        // suggest user is trying to probe the API for security weaknesses.
        if( oldStatusCode.equals(ApplicationNotificationEnums.ORDER_PLACED) && !newStatusCode.equals(ApplicationNotificationEnums.ORDER_CANCEL_REQUESTED))
            throw new AccessDeniedException(String.format(STATUS_ERROR_STRING,buyerUsername,oldStatusCode,newStatusCode ));
    }

    private void performSellerChecks(String sellerUsername, ApplicationNotificationEnums newStatusCode, ApplicationNotificationEnums oldStatusCode) throws AccessDeniedException
    {
        //If buyer is trying to request cancellation on behalf of seller, this indicates that that the buyer is attempting
        //to tamper with the api (or there is a bug), as this option is not, or should not, be available on the front end.
        if(newStatusCode.equals(ApplicationNotificationEnums.ORDER_CANCEL_REQUESTED))
            throw new AccessDeniedException(String.format(STATUS_ERROR_STRING,sellerUsername,oldStatusCode,newStatusCode ));
        else if(oldStatusCode.equals(ApplicationNotificationEnums.ORDER_CANCEL_ACCEPTED) || oldStatusCode.equals(ApplicationNotificationEnums.ORDER_ACCEPTED))
            throw new AccessDeniedException(String.format(STATUS_ERROR_STRING,sellerUsername,oldStatusCode.name(),newStatusCode.name()));
    }

    private void publishOrderStatusChangeEvent(ApplicationNotificationEnums orderStatusChange, ProductOrder orderToUpdate) throws OperationNotSupportedException {
        String seller = orderToUpdate.getSecondaryOwningUsername();
        String buyer = orderToUpdate.getOwningUsername();
        String productName = orderToUpdate.getProductOrderProduct().getProductName();

        NotificationEvent notificationEventToPublish = null;

        switch (orderStatusChange)
        {
            case ORDER_CANCEL_REQUESTED:
            case ORDER_PLACED:
                notificationEventToPublish = new NotificationEvent(orderToUpdate,seller,buyer,productName,orderStatusChange);
                break;
            case ORDER_CANCEL_ACCEPTED:
            case ORDER_ACCEPTED:
                notificationEventToPublish = new NotificationEvent(orderToUpdate,buyer,seller,productName, ApplicationNotificationEnums.ORDER_CANCEL_REQUESTED);
                break;
            default:
                throw new OperationNotSupportedException(String.format(ORDER_STATUS_NOT_SUPPORTED,orderStatusChange.name()));
        }

        this.applicationEventPublisher.publishEvent(notificationEventToPublish);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public ProductOrder afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, UUID entitySurrogateId) throws Http403Exception, NotSupportedException {
        return this.findBySurrogateId(entitySurrogateId);
    }

    /**
     * afterReturningIsOwningUserCheck is used to identify if the user is the owning or secondary owning user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entitySurrogateId The entity  surrogateId
     * @return a true Boolean indicating that the authenticatedUser is the owningUser, or false if the user
     * is the secondary user. Before calling this method, it's important to ensure the user is either the owningUser
     * or the secondaryOwningUser.
     */
    @Override
    public ProductOrder afterReturningIsOwningUserOrSecondaryUserCheck(String authenticatedUser, UUID entitySurrogateId) throws NotSupportedException {
        return this.findBySurrogateId(entitySurrogateId);
    }

    @Override
    public ProductOrder beforeOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, ProductOrder entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    @Override
    public ProductOrder beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(String authenticatedUser, ProductOrder entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    @Override
    public ProductOrder beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck(String authenticatedUser, ProductOrder entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck ensures a user is neither the primary or secondary owning user.
     *
     * @param authenticatedUser the user to permission check.
     * @param entity            the user to check permissions for.
     * @return entity the entity passed as a parameter.
     * @throws Http403Exception      if the user is not listed as secondaryUser and primaryOwningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service
     */
    @Override
    public ProductOrder beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(String authenticatedUser, ProductOrder entity) throws Http403Exception, NotSupportedException {
        return entity;
    }
}
