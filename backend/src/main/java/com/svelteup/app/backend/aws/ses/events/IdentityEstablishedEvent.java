package com.svelteup.app.backend.aws.ses.events;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import com.svelteup.app.backend.utils.events.OwningUserEvent;

/**
 * IdentityEstablishedEvent is published when the user has entered in their contact info allowing us to
 * create a braintree customer.
 */

public class IdentityEstablishedEvent extends OwningUserEvent {

    SvelteUpUserAccountDto userIdentityDataDto;
    public IdentityEstablishedEvent(SvelteUpUser source, SvelteUpUserAccountDto initDataDto) {
        super(source, source.getPassword());
        this.userIdentityDataDto = initDataDto;
    }
}
