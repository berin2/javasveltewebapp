package com.svelteup.app.backend.userlifecycle.events;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.events.OwningUserEvent;
import com.svelteup.app.backend.utils.events.OwningUserSvelteUpUserEvent;

public class GrantFullyAuthorizedAuthorityEvent extends OwningUserSvelteUpUserEvent {

    public GrantFullyAuthorizedAuthorityEvent(SvelteUpUser source) {
        super(source);
    }
}
