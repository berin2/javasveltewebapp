package com.svelteup.app.backend.aws.ses.events;

import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SSvelteUpUserProfile;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
public class IdentityEstablishedEventListener implements
        ApplicationListener<IdentityEstablishedEvent>
{
    protected final SSvelteUpUserProfile svelteUpUserProfile;

    @SneakyThrows
    @Override
    public void onApplicationEvent(IdentityEstablishedEvent event) {
        SvelteUpUserAccountDto identityDto = event.userIdentityDataDto;
        SvelteUpUserProfile userProfile = svelteUpUserProfile.findById(event.getOwningUsername());

    }
}
