package com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions;

/*
* The Http404Exception is thrown when the user requests data (not a non existant URI) that could not be located in the
* application. For example, if the user requests, /existing_resource_type/{non_existing_resource_id}, this exception
* should be thrown.
* */
public class Http404Exception extends RuntimeException {
    public Http404Exception() {
        super("The client requested a resource which could not be located.");
    }

    public Http404Exception(String msg) {
        super(msg);
    }
}
