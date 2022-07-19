package com.svelteup.app.backend.productorder.controllers;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.productorder.controllers.controllerinterfaces.IProducOrderStatusController;
import com.svelteup.app.backend.productorder.dto.PutProductOrderStatusDto;
import com.svelteup.app.backend.productorder.services.SProductOrderStatus;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;

@RestController(ApplicationApi.PRODUCT_ORDER_STATUS)
public class ProductOrderStatusController implements IProducOrderStatusController {
    SProductOrderStatus sProductOrderStatus;

    @Override
    @PutMapping
    public void put(SvelteUpUser authenticatedUser, PutProductOrderStatusDto putProductOrderStatusDto) throws Http400Exception, OperationNotSupportedException, NotSupportedException {
        sProductOrderStatus.put(authenticatedUser.getUsername(),putProductOrderStatusDto);
    }
}
