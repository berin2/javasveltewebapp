package com.svelteup.app.backend.profile.controller;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.io.IOException;

/**
 * ISocialProfileController exposes endpoints for managing SocialProfile HostDescriptor.
 */
public interface ISocialProfileController extends HttpController<Object, SvelteUpUserAccountDto,Object> {
    @Override
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, Object o) throws NotSupportedException;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(ApplicationApi.ACCOUNT_SOCIALPROFILE)
    @Override
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody SvelteUpUserAccountDto putUserContactDto) throws NotSupportedException;

    @Override
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, Object o);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(ApplicationApi.ACCOUNT_SOCIALPROFILE)
    public ResponseEntity<SvelteUpUserAccountDto> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser) throws NotSupportedException, IOException;
}
