package com.svelteup.app.backend.profile.services;

import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUsernameService;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;

@Service()
@EqualsAndHashCode()
@RequiredArgsConstructor()
public class SUserProfileAddress extends  SHttpExceptionThrower  implements HttpUsernameService<SvelteUpUserAccountDto, SvelteUpUserAccountDto> {

    protected final SSvelteUpUserProfile svelteUpUserProfileService;
    protected final SImageS3 sImageS3;

    @Override
    public void post(String authenticatedUser, SvelteUpUserAccountDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
       this.throwUnsupportedOperationException(this.getClass().toString(),"post",authenticatedUser);
    }

    @Override
    public ResponseEntity<SvelteUpUserAccountDto> get(String username) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException
    {
        SvelteUpUserProfile discoveredAddress = this.svelteUpUserProfileService.findBySurrogateIdWithCheck(username,username);
        SvelteUpUserAccountDto putAddressDto = discoveredAddress.toAddressDto();
        return ResponseEntity.ok(putAddressDto);
    }

    @Override
    public void put(String authenticatedUser, SvelteUpUserAccountDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        SvelteUpUserProfile discoveredProfile = this.svelteUpUserProfileService.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
        discoveredProfile.updateAddress(update_DTO);
    }

    @Override
    public void delete(String username) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException {
        this.throwUnsupportedOperationException(this.getClass().toString(),"delete",username);
    }

    public String getProfileImage(String profileImage) throws IOException {
        String image = this.sImageS3.getSingleImage(SSvelteUpUserProfile.class,profileImage,0,null);
        return image;
    }

}
