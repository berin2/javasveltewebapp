package com.svelteup.app.backend.productorder.controllers;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.interfaces.IProductOrderController;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.dto.PutProductOrderDto;
import com.svelteup.app.backend.productorder.dto.PutProductOrderStatusDto;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.productorder.services.SProductOrder;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import java.util.List;

@RestController()
@AllArgsConstructor
public class ProductOrderController implements IProductOrderController {
    SProductOrder productOrderService;
    @Override
    public void post(SvelteUpUser authenticatedUser, PostProductOrderDto postProductOrderDto) throws NotSupportedException {
        this.productOrderService.post(authenticatedUser.getUsername(), postProductOrderDto);
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, PutProductOrderStatusDto putProductOrderStatusDto) throws NotSupportedException {
        this.productOrderService.put(authenticatedUser.getUsername(),putProductOrderStatusDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, UuidDto uuidDto) {

    }

    @GetMapping(path = ApplicationApi.PRODUCT_ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PutProductOrderDto>> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser) throws NotSupportedException {
        return ResponseEntity.ok(this.productOrderService.getAllProductOrdersByUsername(authenticatedUser.getUsername()));
    }
}
