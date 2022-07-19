package com.svelteup.app.backend.security.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
* Defines Authentication scheme and potential exception handling mechanisms for
* exceptions thrown by SpringSecurity
*
* */
@Component
@ControllerAdvice
public class SecurityAuthenticationPoint implements AuthenticationEntryPoint {
    SessionRepository sessionRepository;
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        HttpSession requestSession = httpServletRequest.getSession(false);

        if(e instanceof AuthenticationCredentialsNotFoundException) {
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            Cookie sessionReplacementCookie = new Cookie("SESSION",null);
            sessionReplacementCookie.setHttpOnly(true);
            httpServletResponse.addCookie(sessionReplacementCookie);
        }
        else
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler({SessionAuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void unauthHandler()
    {
        String stringer = "Debug";
    }

}
