package com.svelteup.app.backend.utils.events;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.Getter;

/**
 * OwningUserSvelteUpUserEvent is a base event that extends OwningUserEvent. It adds a payload of
 * SvelteUpUserProfile and is intended to be used in situations where the SvelteUpUser is required, such
 * as in the case of VerificationEvents.
 */
public class OwningUserSvelteUpUserEvent extends OwningUserEvent
{
    @Getter
    protected SvelteUpUser owningSvelteUpUserObject;

    public OwningUserSvelteUpUserEvent(SvelteUpUser source) {
        super(source, source.getUsername());
        this.owningSvelteUpUserObject = source;
    }
}
