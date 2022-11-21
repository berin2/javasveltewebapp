package com.svelteup.app.backend.userlifecycle.controllers;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.dtostores.AppInitDto;
import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface IInitializerController {

    @PostMapping(ApplicationApi.REGISTER)
    @ResponseStatus(HttpStatus.OK)
    void postRegister(@RequestBody UserRegisterDto userRegisterDto);
    @PostMapping(ApplicationApi.POST_CUSTOMER_INITIALIZE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<AppInitDto> postUserIdentity(@AuthenticationPrincipal SvelteUpUser user, @RequestBody SvelteUpUserProfileDto dto);
    @PostMapping(ApplicationApi.POST_PHONE_NUMBER_VERIFY)
    @ResponseStatus(HttpStatus.OK)
    void postPhoneNumberVerify(@AuthenticationPrincipal SvelteUpUser user, @RequestBody SvelteUpUserProfileDto dto);
    @GetMapping(ApplicationApi.GET_EMAIL_VERIFY)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<AppInitDto> getEmailVerify(@PathVariable UUID emailToken);
}
