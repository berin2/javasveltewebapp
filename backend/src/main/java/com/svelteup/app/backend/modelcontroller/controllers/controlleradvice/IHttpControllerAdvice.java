package com.svelteup.app.backend.modelcontroller.controllers.controlleradvice;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 *This class is used to provide controller advice when application HttpExceptions have been
 * */
public interface IHttpControllerAdvice extends ValidationErrorControllerAdvice{
    // 400s start
    @ExceptionHandler(Http400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void sc400Exception(Http400Exception exception);

    @ExceptionHandler(Http401Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void sc401Exception(Http401Exception http401Exception);

    @ExceptionHandler(Http403Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void sc403Exception(Http403Exception http403Exception);

    @ExceptionHandler(Http404Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void sc404Exception(Http404Exception http404Exception);

    //500s start
    @ExceptionHandler(Http500Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void sc500Exception(Http500Exception http500Exception);


}
