package com.svelteup.app.backend.profile.controller;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.profile.dtos.AddressDto;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
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
 * IUserAddressController exposes API endpoints for managing user address HostDescriptor.
 */
public interface IUserAddressController extends HttpController<AddressDto, AddressDto,AddressDto> {
    @Override
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, AddressDto dto) throws NotSupportedException;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(ApplicationApi.ACCOUNT_ADDRESS)
    @Override
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody AddressDto putUserAddressDto) throws NotSupportedException;

    @Override
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, AddressDto dto);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ApplicationApi.ACCOUNT_ADDRESS)
    ResponseEntity<AddressDto> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser) throws NotSupportedException;
}
