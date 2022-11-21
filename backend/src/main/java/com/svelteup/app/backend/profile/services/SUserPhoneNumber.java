package com.svelteup.app.backend.profile.services;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUsernameService;
import com.svelteup.app.backend.profile.dtos.PhoneNumberDto;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;

@Service
@EqualsAndHashCode(callSuper = true)
public class SUserPhoneNumber extends SHttpExceptionThrower  implements HttpUsernameService<PhoneNumberDto, SvelteUpUserProfileDto>
{
    SSvelteUpUserProfile sSvelteUpUserProfile;

    public SUserPhoneNumber(SSvelteUpUserProfile profile)
    {
        this.sSvelteUpUserProfile = profile;
    }

    @Override
    public void post(String authenticatedUser, SvelteUpUserProfileDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        this.throwHttp405("post",this.getClass().toString(),authenticatedUser);
    }

    @Override
    public ResponseEntity<PhoneNumberDto> get(String authenticatedUsername) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        SvelteUpUserProfile discoverdPhoneNumber = this.sSvelteUpUserProfile.findBySurrogateIdWithCheck(authenticatedUsername,authenticatedUsername);
        PhoneNumberDto returnDto = discoverdPhoneNumber.toPutPhoneNumberDto();
        return ResponseEntity.ok(returnDto);
    }

    @Override
    public void put(String authenticated_user, PhoneNumberDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        SvelteUpUserProfile discoveredPhoneNumber = this.sSvelteUpUserProfile.findBySurrogateIdWithCheck(authenticated_user, authenticated_user);
        discoveredPhoneNumber.updatePhoneNumber(update_DTO);
    }

    @Override
    public void delete(String username) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException {
        this.throwUnsupportedOperationException(this.getClass().toString(),"delete",username);
    }

}
