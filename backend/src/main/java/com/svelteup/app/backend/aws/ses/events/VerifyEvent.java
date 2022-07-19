package com.svelteup.app.backend.aws.ses.events;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.events.OwningUserEvent;
import com.svelteup.app.backend.utils.events.OwningUserSvelteUpUserEvent;

/**
 * VerifyEvent is broadcasted when a user is given the email verified authority to allow
 * the creation of required verified user entites such as payment info objects, social profile objects, and etc..
 */
public class VerifyEvent extends OwningUserSvelteUpUserEvent {
    public VerifyEvent(SvelteUpUser source, String owningUsername) {
        super(source);
    }
}
