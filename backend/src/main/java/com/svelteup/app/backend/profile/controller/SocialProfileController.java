package com.svelteup.app.backend.profile.controller;

import com.svelteup.app.backend.modelcontroller.controllers.abstractcontroller.AbstractController;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.profile.services.SUserProfile;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import java.io.IOException;

@RestController
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
/**
 * SocialProfileController is used to perform CRUD operations on a user's Social Profile.
 */
public class SocialProfileController extends AbstractController implements ISocialProfileController {

    private SUserProfile userProfileService;


    @Override
    public void post(SvelteUpUser authenticatedUser, Object o) throws NotSupportedException {
        this.exceptionThrowerService.throwHttp405("post",this.getClass().toString(),authenticatedUser.getUsername());
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, SvelteUpUserAccountDto putUserContactDto) throws NotSupportedException {
        this.userProfileService.put(authenticatedUser.getUsername(), putUserContactDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, Object o) {
        this.exceptionThrowerService.throwHttp405("delete",this.getClass().toString(),authenticatedUser.getUsername());
    }

    public ResponseEntity<SvelteUpUserAccountDto> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser) throws NotSupportedException, IOException {
        ResponseEntity<SvelteUpUserAccountDto> responseEntity = this.userProfileService.get(authenticatedUser.getUsername());
        return responseEntity;
    }

}
