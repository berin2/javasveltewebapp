package com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions;

/*
 * The Http403Exception is thrown when the user is authenticated, trying to access a resource,
 * but the user does not have the required system priviliges to access said resource.
 * */
public class Http403Exception extends RuntimeException {
    public Http403Exception() {
        super("An authenticated user attempted to access an api resource they did not have priviliges to access");
    }

    public Http403Exception(String msg) {
        super(msg);
    }
}
