package com.svelteup.app.backend.utils.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * OwningUserThymeEvent is an event class that extends the owninguser event but also adds an HttpServletRequest
 * and response on top to faciliate ThymeLeafTemeplate processing.
 */
public class OwningUserThymeEvent extends OwningUserEvent {
    @Getter
    protected HttpServletRequest request;
    @Getter
    protected HttpServletResponse response;

    public OwningUserThymeEvent(Object source, HttpServletRequest req, HttpServletResponse resp, String owningUsername) {
        super(source, owningUsername);
        this.request = req;
        this.response = resp;
    }
}
