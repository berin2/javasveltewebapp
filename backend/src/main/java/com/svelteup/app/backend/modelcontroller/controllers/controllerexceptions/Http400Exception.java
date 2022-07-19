package com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions;

/*
 * This exception is thrown when the user submits an invalid input into the API.
 * */
public class Http400Exception extends RuntimeException {


    public Http400Exception() {
        super("The user provided bad input to the API which resulted in response code 400.");
    }

    public Http400Exception(String msg) {
        super(msg);
    }


}
