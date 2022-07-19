package com.svelteup.app.backend.security.events;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.events.OwningUserEvent;
import org.springframework.context.ApplicationEvent;

public class UpdateSessionEvent extends OwningUserEvent {
    public SvelteUpUser userWithUpdatedSessionValues;
    public UpdateSessionType updateType;
    public UpdateSessionEvent(Object source,SvelteUpUser updatedSessionUser,UpdateSessionType type) {
        super(source,updatedSessionUser.getUsername());
        this.updateType = type;
        this.userWithUpdatedSessionValues = updatedSessionUser;
    }
}
