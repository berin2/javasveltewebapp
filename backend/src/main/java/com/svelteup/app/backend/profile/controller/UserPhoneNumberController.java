package com.svelteup.app.backend.profile.controller;

import com.svelteup.app.backend.modelcontroller.controllers.abstractcontroller.AbstractController;
import com.svelteup.app.backend.profile.dtos.PhoneNumberDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.profile.services.SUserPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;

@RestController()
@AllArgsConstructor()
@EqualsAndHashCode( callSuper = true)
public class UserPhoneNumberController extends AbstractController implements IUserPhoneNumberController{

    SUserPhoneNumber userPhoneNumberService;

    @Override
    public void post(SvelteUpUser authenticatedUser, PhoneNumberDto o) throws NotSupportedException {
        this.exceptionThrowerService.throwHttp405("post",this.getClass().toString(),authenticatedUser.getUsername());
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, PhoneNumberDto putPhoneNumberDto) throws NotSupportedException {
        this.userPhoneNumberService.put(authenticatedUser.getUsername(),putPhoneNumberDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, PhoneNumberDto phoneNumberDto) {
        this.exceptionThrowerService.throwHttp405("delete",this.getClass().toString(),authenticatedUser.getUsername());
    }

    @Override
    public ResponseEntity<PhoneNumberDto> get(SvelteUpUser authenticatedUser) throws NotSupportedException {
        return this.userPhoneNumberService.get(authenticatedUser.getUsername());
  }
}
