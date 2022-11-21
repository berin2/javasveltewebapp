package com.svelteup.app.backend.profile.controller.email;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.NotSupportedException;

public interface IEmailController  {
    @PutMapping(value= ApplicationApi.PUT_ACCOUNT_VERIFICATION_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    void resendVerificationEmail(@AuthenticationPrincipal SvelteUpUser user) throws Http400Exception, Http500Exception, NotSupportedException;
    @PutMapping(value= ApplicationApi.PUT_ACCOUNT_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    void updateVerificationTokenAndResendEmail(@AuthenticationPrincipal SvelteUpUser user, @RequestBody SvelteUpUserProfileDto dto) throws NotSupportedException;
}
