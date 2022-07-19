package com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions;

/*
 * The Http500Exception is thrown when the server experiences an internal error.
 * */
public class Http500Exception extends RuntimeException {
    public Http500Exception() {
        super("The application experienced an internal system error while servicing an API call.");
    }

    public Http500Exception(String msg) {
        super(msg);
    }

}
