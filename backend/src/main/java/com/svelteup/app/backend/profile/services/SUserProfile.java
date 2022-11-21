package com.svelteup.app.backend.profile.services;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.*;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUsernameService;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;

@Service
@EqualsAndHashCode(callSuper = true)
public class SUserProfile extends SHttpExceptionThrower implements HttpUsernameService<SvelteUpUserProfileDto,Object>
{
    protected final SSvelteUpUserProfile userProfileService;
    protected final SImageS3 s3ImageService;

    @Value("${spring.profiles.active}")
    protected String profile;

    public SUserProfile(SSvelteUpUserProfile userProfileService,SImageS3 s3ImageService)
    {
        super();
        this.userProfileService =  userProfileService;
        this.s3ImageService = s3ImageService;
    }

    @Override
    public void post(String authenticatedUser, Object create_DTO) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException {
        this.throwHttp405("post",this.getClass().toString(),authenticatedUser);
    }

    @Override
    public ResponseEntity<SvelteUpUserProfileDto> get(String username) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException, IOException {
        SvelteUpUserProfile discoveredProfile = this.userProfileService.findBySurrogateIdWithCheck(username,username);
        String image = "";

        if(profile.equals(ApplicationApi.DEV_PROFILE))
            image = s3ImageService.getTestProfileImage();
        else
            image = s3ImageService.getSingleImage(SvelteUpUserProfile.class,username,0,null);
        SvelteUpUserProfileDto discoveredUserProfileDto = discoveredProfile.toUserProfileDto(image);
        return ResponseEntity.ok(discoveredUserProfileDto);
    }

    @Override
    public void put(String authenticated_user, SvelteUpUserProfileDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException {
        SvelteUpUserProfile discoveredProfile = this.userProfileService.findBySurrogateIdWithCheck(authenticated_user,authenticated_user);
        discoveredProfile.updateUserProfileDto(update_DTO);
    }

    @Override
    public void delete(String username) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException {
        this.throwUnsupportedOperationException(this.getClass().toString(),"delete",username);
    }

    public SvelteUpUserProfileDto getProfileDto(String username,String s3Image) throws NotSupportedException {
       SvelteUpUserProfile discoveredProfile = this.userProfileService.findById(username);
       SvelteUpUserProfileDto returnDto =  new SvelteUpUserProfileDto(discoveredProfile,s3Image);
       return returnDto;
    }

}
