package com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions;

/*
 * This exception is throw when a user attempts to access a resource requiring authentication,
 * but the user is not authenticated.
 * */
public class Http401Exception extends RuntimeException {

    public Http401Exception() {
        super("The user attempted to access a resource requiring authentication,but the user was not authenticated.");
    }

    public Http401Exception(String msg) {
        super(msg);
    }
}
