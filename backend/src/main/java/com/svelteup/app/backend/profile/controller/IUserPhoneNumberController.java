package com.svelteup.app.backend.profile.controller;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.NotSupportedException;

/**
 * IUserPhoneNumberController exposes endpoints for managing PhoneNumber HostDescriptor.
 */
public interface IUserPhoneNumberController extends HttpController<Object, SvelteUpUserAccountDto,Object> {
    @Override
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, Object o) throws NotSupportedException;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(ApplicationApi.ACCOUNT_PHONE)
    @Override
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody SvelteUpUserAccountDto putUserAddressDto) throws NotSupportedException;

    @Override
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, Object o);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ApplicationApi.ACCOUNT_PHONE)
    ResponseEntity<SvelteUpUserAccountDto> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser) throws NotSupportedException;
}
