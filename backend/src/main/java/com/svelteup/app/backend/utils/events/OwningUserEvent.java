package com.svelteup.app.backend.utils.events;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

public class OwningUserEvent extends ApplicationEvent {

    @Getter
    protected String owningUsername;

    public OwningUserEvent(Object source,String owningUsername) {
        super(source);
        this.owningUsername = owningUsername;
    }

}
