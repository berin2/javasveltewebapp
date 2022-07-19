package com.svelteup.app.backend.profile.controller;

import com.svelteup.app.backend.modelcontroller.controllers.abstractcontroller.AbstractController;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.profile.services.SUserProfileAddress;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;

@RestController()
@AllArgsConstructor()
@EqualsAndHashCode(callSuper = true)
/**
 * UserAddressController is used to update a User's address.
 */
public class UserAddressController extends AbstractController implements IUserAddressController {
    protected SUserProfileAddress sUserProfileAddress;

    @Override
    public void post(SvelteUpUser authenticatedUser, Object o) throws NotSupportedException {
        this.exceptionThrowerService.throwHttp405("post",this.getClass().toString(),authenticatedUser.getUsername());
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, SvelteUpUserAccountDto putUserAddressDto) throws NotSupportedException {
        this.sUserProfileAddress.put(authenticatedUser.getUsername(),putUserAddressDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, Object o) {
        this.exceptionThrowerService.throwHttp405("delete",this.getClass().toString(),authenticatedUser.getUsername());
    }

    @Override
    public ResponseEntity<SvelteUpUserAccountDto> get(SvelteUpUser authenticatedUser) throws NotSupportedException {
        return this.sUserProfileAddress.get(authenticatedUser.getUsername());
    }
}
