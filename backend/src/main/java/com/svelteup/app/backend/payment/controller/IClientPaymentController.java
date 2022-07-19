package com.svelteup.app.backend.payment.controller;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.payment.dtos.CreditCardUpdateDto;
import com.svelteup.app.backend.payment.dtos.PaymentMethodStoreDto;
import com.svelteup.app.backend.payment.dtos.PaymentMethodDeleteDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;

public interface IClientPaymentController extends HttpController<CreditCardUpdateDto,CreditCardUpdateDto, CreditCardUpdateDto>{

    @PostMapping(ApplicationApi.CLIENT_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser,@RequestBody CreditCardUpdateDto creditCardUpdateDto) throws Http405Exception, NotSupportedException;
    @PutMapping(ApplicationApi.CLIENT_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser,@RequestBody  CreditCardUpdateDto creditCardUpdateDto) throws Http400Exception, NotSupportedException;
    @DeleteMapping(ApplicationApi.CLIENT_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody CreditCardUpdateDto deleteDto) throws Http405Exception, NotSupportedException;
    @GetMapping(ApplicationApi.CLIENT_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PaymentMethodStoreDto> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser) throws NotSupportedException;
}
