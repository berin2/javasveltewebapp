package com.svelteup.app.backend.modelcontroller.controllers.controlleradvice;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Locale;

@ControllerAdvice()
public class IHttpControllerAdviceImpl implements IHttpControllerAdvice {

    @Override
    public void sc400Exception(Http400Exception exception) {
        System.out.println(exception.getMessage().toLowerCase(Locale.ROOT));
    }

    @Override
    public void sc401Exception(Http401Exception exception) {
        System.out.println(exception.getMessage().toLowerCase(Locale.ROOT));
    }

    @Override
    public void sc403Exception(Http403Exception exception) {
        System.out.println(exception.getMessage().toLowerCase(Locale.ROOT));
    }

    @Override
    public void sc404Exception(Http404Exception exception) {
        System.out.println(exception.getMessage().toLowerCase(Locale.ROOT));
    }

    @Override
    public void sc500Exception(Http500Exception exception) {
        System.out.println(exception.getMessage().toLowerCase(Locale.ROOT));
    }

    @Override
    public void respondToBindException() {

    }
}
