package com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions;

/*
* Exception thrown in controller when a particular http exception is not handled by a particular
* API endpoint.
* */
public class Http405Exception extends RuntimeException{
    public Http405Exception(String message)
    {
        super(message);
    }
}
