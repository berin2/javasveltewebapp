package com.svelteup.app.backend.modelcontroller.controllers.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* ValidationErrorControllerAdvice is used to define ExceptionHandlers for
* routes which throw errors related to JSR Validation.
* */
public interface ValidationErrorControllerAdvice
{
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void respondToBindException();
}
