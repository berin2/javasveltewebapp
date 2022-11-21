package com.svelteup.app.backend.profile.controller.account;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.profile.dtos.ContactDto;
import com.svelteup.app.backend.profile.dtos.DeleteAccountDto;
import com.svelteup.app.backend.profile.dtos.PasswordChangeDto;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IAccountController {
    /**
     *Updates a phone number and sends a verification code text to the registered phone number.
     * @param profile auth user
     * @param passwordChangeDto contains passwordChangeInfo
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @PutMapping(value = ApplicationApi.PASSWORD,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void putPassword(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody PasswordChangeDto passwordChangeDto) throws Http401Exception, Http403Exception, Http500Exception;

    /**
     *Updates a phone number and sends a verification code text to the registered phone number.
     * @param profile auth user
     * @param accountDto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @PutMapping(value = ApplicationApi.POST_PHONE_NUMBER,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void putPhoneNumber(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody SvelteUpUserProfileDto accountDto) throws Http401Exception, Http403Exception, Http500Exception;

    /**
     *Deletes Account and sends a verification code text to the registered email.
     * @param profile auth user
     * @param deleteAccount contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @DeleteMapping(value = ApplicationApi.ACCOUNT,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void deleteAccount(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody DeleteAccountDto deleteAccount) throws Http401Exception, Http403Exception, Http500Exception;

    /**
     *Updates email and sends a verification code text to the registered email.
     * @param profile auth user
     * @param dto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @PutMapping(value = ApplicationApi.POST_FOE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void putFoe(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody ContactDto dto) throws Http401Exception, Http403Exception, Http500Exception, NotSupportedException;


    /**
     *Updates email and sends a verification code text to the registered email.
     * @param profile auth user
     * @param dto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @DeleteMapping(value = ApplicationApi.POST_FOE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void deleteFoe(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody List<ContactDto> dto) throws Http401Exception, Http403Exception, Http500Exception, NotSupportedException;


    /**
     *Updates email and sends a verification code text to the registered email.
     * @param profile auth user
     * @param accountDto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @PutMapping(value = ApplicationApi.TWO_FACTOR_AUTH,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void putTwoFactorAuth(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody SvelteUpUserProfileDto accountDto) throws Http401Exception, Http403Exception, Http500Exception;

    /**
     *Updates email and sends a verification code text to the registered email.
     * @param profile auth user
     * @param accountDto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @PutMapping(value = ApplicationApi.POST_EMAIL,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void putEmail(@AuthenticationPrincipal SvelteUpUser profile, @RequestBody SvelteUpUserProfileDto accountDto) throws Http401Exception, Http403Exception, Http500Exception, IOException, NotSupportedException;


   // @GetMapping(value  = ApplicationApi.GET_EMAIL_VERIFY)
    @ResponseStatus(HttpStatus.OK)
    void verifyEmail(@PathVariable UUID emailToken) throws NotSupportedException;
    @PutMapping(value= ApplicationApi.PUT_ACCOUNT_VERIFICATION_TOKEN)
    @ResponseStatus(HttpStatus.OK)
    void resendVerificationEmail(@AuthenticationPrincipal SvelteUpUser user) throws Http400Exception, Http500Exception, NotSupportedException;
    @PutMapping(value= ApplicationApi.PUT_ACCOUNT_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    void updateVerificationTokenAndResendEmail(@AuthenticationPrincipal SvelteUpUser user, @RequestBody SvelteUpUserProfileDto dto) throws NotSupportedException;
}
