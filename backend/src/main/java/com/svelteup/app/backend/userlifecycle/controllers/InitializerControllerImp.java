package com.svelteup.app.backend.userlifecycle.controllers;

import com.svelteup.app.backend.dtostores.AppInitDto;
import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.NavDtoService;
import com.svelteup.app.backend.userlifecycle.services.initializerchains.IdentityInitializedInitializerChain;
import com.svelteup.app.backend.userlifecycle.services.initializerchains.RegistrationInitializerChain;
import com.svelteup.app.backend.userlifecycle.services.initializerchains.VerifyEmailInitializerChain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class InitializerControllerImp implements IInitializerController {
    protected final RegistrationInitializerChain registrationInitializerChain;
    protected final IdentityInitializedInitializerChain identityInitializedInitializerChain;
    protected final VerifyEmailInitializerChain verifyEmailInitializerChain;
    protected final NavDtoService navDtoService;

    @Override
    public void postRegister(UserRegisterDto userRegisterDto) {
        registrationInitializerChain.doChain(null,userRegisterDto);
    }

    @Override
    public ResponseEntity<AppInitDto> postUserIdentity(SvelteUpUser user, SvelteUpUserProfileDto dto) {
        EstablishCustomerIdentityDto establishDto = new EstablishCustomerIdentityDto();
        establishDto.user = user;
        establishDto.svelteUpUserProfileDto = dto;
        identityInitializedInitializerChain.doChain(null,establishDto);
        return ResponseEntity.ok(navDtoService.buildNavStoreDtoWithoutNotificationsAndWithoutImages(establishDto.user));
    }

    @Override
    public void postPhoneNumberVerify(SvelteUpUser user, SvelteUpUserProfileDto dto) {

    }

    @Override
    public ResponseEntity<AppInitDto> getEmailVerify(UUID emailToken) {
        EmailVerifyInitializerDto dto =  new EmailVerifyInitializerDto();
        dto.emailToken =emailToken;
        this.verifyEmailInitializerChain.doChain(null,dto);

        return null;
    }
}
