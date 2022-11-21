package com.svelteup.app.backend.aws.ses.events;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.utils.events.OwningUserEvent;

/**
 * IdentityEstablishedEvent is published when the user has entered in their contact info allowing us to
 * create a braintree customer.
 */

public class IdentityEstablishedEvent extends OwningUserEvent {

    SvelteUpUserProfileDto userIdentityDataDto;
    public IdentityEstablishedEvent(SvelteUpUser source, SvelteUpUserProfileDto initDataDto) {
        super(source, source.getPassword());
        this.userIdentityDataDto = initDataDto;
    }
}
