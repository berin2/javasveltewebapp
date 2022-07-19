package com.svelteup.app.backend.productorder.services;

import com.svelteup.app.backend.aop.aspects.paireduser.OwningUserPairedNonPkEntityAccessCheck;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUuidService;
import com.svelteup.app.backend.modelcontroller.services.services.SProduct;
import com.svelteup.app.backend.notificationmessage.events.NotificationEvent;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.dto.PutProductOrderDto;
import com.svelteup.app.backend.productorder.dto.PutProductOrderStatusDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.productorder.events.TransferMoneyEvent;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.repositories.RProductOrder;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Data @EqualsAndHashCode(callSuper = false)
public class SProductOrder extends SSurrogateEntity<Long, ProductOrder>
        implements HttpUuidService<PutProductOrderStatusDto,PostProductOrderDto>,
                   ApplicationEventPublisherAware, OwningUserPairedNonPkEntityAccessCheck<ProductOrder>
{
    private static final  String UNSUPPORTED_OPERATION ="% requested to invoke method %s which is not supported in " + SProductOrder.class.toString() + " for Object with id: %s .";
    private static final  String UNSUPPORTED_BUYER_AND_SELLER_OPERATION = "%s requested to create an order but is both the buyer and seller of said Product.";
    private SProductOrderStatus productOrderStatusService;
    private SProduct productService;
    private RProductOrder productOrderRepository;
    private ApplicationEventPublisher eventPublisher;

    public SProductOrder(RProductOrder productOrderRepository, SProductOrderStatus productOrderStatusService, SProduct productService)
    {
        super(productOrderRepository);
        this.productOrderStatusService = productOrderStatusService;
        this.productService = productService;
        this.productOrderRepository = productOrderRepository;
    }

    private void checkIsBuyerAndSeller(String authenticatedBuyerUser, Product orderedProduct) throws Http403Exception
    {
        if(authenticatedBuyerUser.equals(orderedProduct.getOwningUsername()))
            throw new Http403Exception(String.format(UNSUPPORTED_BUYER_AND_SELLER_OPERATION,authenticatedBuyerUser));
    }

    private Double calculateOrderCost(Product orderedProduct, Integer productQuantity)
    {
        return orderedProduct.getProductCost() * productQuantity;
    }

    @Override
    public void post(String authenticatedUser, PostProductOrderDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException {
        Product orderProduct =  this.productService.findBySurrogateId(create_DTO.productId);
        this.checkIsBuyerAndSeller(authenticatedUser,orderProduct);

        Double productOrderCost  = this.calculateOrderCost(orderProduct,create_DTO.productOrderQuantity);
        ProductOrder newProductOrder = new ProductOrder(authenticatedUser,productOrderCost,create_DTO.productOrderQuantity, ApplicationNotificationEnums.ORDER_PLACED,orderProduct);
        repository.save(newProductOrder);

        NotificationEvent notificationEvent = new NotificationEvent(newProductOrder,authenticatedUser,orderProduct.getOwningUsername(),orderProduct.getOwningUsername(), ApplicationNotificationEnums.ORDER_PLACED);
        this.eventPublisher.publishEvent(notificationEvent);

        TransferMoneyEvent orderMoneyTransfer = new TransferMoneyEvent(newProductOrder, newProductOrder.getProductOrderCost(), newProductOrder.getOwningUsername(), newProductOrder.getSecondaryOwningUsername());
        this.eventPublisher.publishEvent(orderMoneyTransfer);
    }

    @Override
    public ResponseEntity<PutProductOrderStatusDto> get(UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException {
        return null;
    }

    public List<PutProductOrderDto> getAllProductOrdersByUsername(String authenticatedUser) throws NotSupportedException {
        List<ProductOrder> productOrderList = this.productOrderRepository.findAllByOwningUsername(authenticatedUser);
        List<PutProductOrderDto> productOrderDtoList = new ArrayList<>();
        PutProductOrderDto iteratorDto = null;

        for(ProductOrder order : productOrderList)
        {
            this.beforeOwningUserPairedNonPrimaryKeyPermissionCheck(authenticatedUser,order);
            iteratorDto = new PutProductOrderDto(order);
            productOrderDtoList.add(iteratorDto);
        }
        return productOrderDtoList;
    }

    @Override
    public void put(String authenticated_user, PutProductOrderStatusDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException
    {
        UUID surrogateId = update_DTO.productOrderId;
        ApplicationNotificationEnums statusCode = update_DTO.productOrderStatusCode;
        ProductOrder productOrderToUpdate = this.findBySurrogateId(surrogateId);

        this.productOrderStatusService.canPerformStatusChange(authenticated_user,statusCode,productOrderToUpdate);

        productOrderToUpdate.setProductOrderStatus(statusCode);
        this.repository.save(productOrderToUpdate);

        if(statusCode.equals(ApplicationNotificationEnums.ORDER_CANCEL_ACCEPTED))
        {
            productOrderToUpdate.setProductOrderStatus(ApplicationNotificationEnums.ORDER_CANCEL_PROCESSED);
            TransferMoneyEvent refundEvent = new TransferMoneyEvent(productOrderToUpdate,productOrderToUpdate.getProductOrderCost(),productOrderToUpdate.getSecondaryOwningUsername(),productOrderToUpdate.getOwningUsername());
            this.eventPublisher.publishEvent(refundEvent);
        }
    }

    @Override
    public void delete(String username, UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException {
        throw new UnsupportedOperationException(String.format(UNSUPPORTED_OPERATION, username, "DELETE", secondary_id.toString()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
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
