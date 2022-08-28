package com.svelteup.app.backend.modelcontroller.controllers.interfaces;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.dto.PutProductOrderStatusDto;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;

public interface IProductOrderController extends HttpController<PostProductOrderDto, PutProductOrderStatusDto, UuidDto> {

    @Override
    @PostMapping(value = ApplicationApi.PRODUCT_ORDER,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PostProductOrderDto postProductOrderDto) throws NotSupportedException;

    @Override
    @PutMapping(ApplicationApi.PRODUCT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser,@RequestBody PutProductOrderStatusDto putProductOrderStatusDto) throws NotSupportedException;

    @Override
    @DeleteMapping(ApplicationApi.PRODUCT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody UuidDto uuidDto);
}
