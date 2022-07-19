package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.profile.services.SSvelteUpUserProfile;
import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.stereotype.Component;

@Component
public class IdentityVerifyAddSvelteUpProfileInitializer extends EntityInitializer<EmailVerifyInitializerDto> {
    protected final SSvelteUpUserProfile svelteUpUserProfile;
    protected final SSvelteUpUser svelteUpUser;

    public IdentityVerifyAddSvelteUpProfileInitializer(SSvelteUpUserProfile svelteUpUserProfile, SSvelteUpUser svelteUpUser) {
        super(false);
        this.svelteUpUserProfile = svelteUpUserProfile;
        this.svelteUpUser = svelteUpUser;
    }

    @Override
    public void initialize(EmailVerifyInitializerDto dto)
    {
        SvelteUpUser user = svelteUpUser.findByUsername(dto.tokenEntity.getOwningUsername());
        svelteUpUserProfile.initializeUser(user);
    }
}
