package com.svelteup.app.backend.productorder.services;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.POwningUserNonPkAccessChecker;
import com.svelteup.app.backend.aop.aspects.paireduser.PPairedOwningUserNonPkAccessChecker;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
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
                   ApplicationEventPublisherAware
{
    private static final  String UNSUPPORTED_OPERATION ="% requested to invoke method %s which is not supported in " + SProductOrder.class.toString() + " for Object with id: %s .";
    private static final  String UNSUPPORTED_BUYER_AND_SELLER_OPERATION = "%s requested to create an order but is both the buyer and seller of said Product.";
    private SProductOrderStatus productOrderStatusService;
    private SProduct productService;
    private RProductOrder productOrderRepository;
    private ApplicationEventPublisher eventPublisher;
    protected POwningUserNonPkAccessChecker pOwningUserNonPkAccessChecker;
    protected PPairedOwningUserNonPkAccessChecker pPairedOwningUserNonPkAccessChecker;


    public SProductOrder(RProductOrder productOrderRepository, SProductOrderStatus productOrderStatusService,POwningUserNonPkAccessChecker accessChecker)
    {
        super(productOrderRepository);
        this.productOrderStatusService = productOrderStatusService;
        this.productOrderRepository = productOrderRepository;
        this.pOwningUserNonPkAccessChecker = accessChecker;
    }

    @Autowired
    public void setProductService(SProduct productService)
    {
        this.productService = productService;
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
            this.pOwningUserNonPkAccessChecker.afterReturningOwningUserNonPrimaryKeyPermissionCheck(authenticatedUser,order);
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

}
