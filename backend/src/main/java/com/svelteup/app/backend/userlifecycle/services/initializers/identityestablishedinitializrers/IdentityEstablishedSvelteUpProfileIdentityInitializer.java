package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SSvelteUpUserProfile;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;

@Component
public class IdentityEstablishedSvelteUpProfileIdentityInitializer extends EntityInitializer<EstablishCustomerIdentityDto> {
    protected final SSvelteUpUserProfile profileService;
    public IdentityEstablishedSvelteUpProfileIdentityInitializer(SSvelteUpUserProfile profileService) {
        super(false);
        this.profileService = profileService;
    }

    @Override
    public void initialize(EstablishCustomerIdentityDto identityDto) throws NotSupportedException {
        SvelteUpUserProfile userProfile = profileService.initializeUser(identityDto.user);
        userProfile.initializeSvelteUpUserProfile(identityDto.svelteUpUserProfileDto);
        identityDto.userProfile = userProfile;
    }
}
