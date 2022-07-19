package com.svelteup.app.backend.security.services;

import com.svelteup.app.backend.api.ApplicationApi;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/*
* UrlMatcherService is used to identify if a request matches a particular URI and METHOD
* combination.
* */
@Component
public class UrlMatcherService {

    public boolean isLoginRequest(HttpServletRequest httpServletRequest)  throws RuntimeException
    {
        if(httpServletRequest == null)
            throw new RuntimeException("Null HttpServletRequest passed to UrlMatcherService isLoginRequest method.");

        String httpMethod = httpServletRequest.getMethod().toUpperCase();
        String requestUri = httpServletRequest.getRequestURI();
        boolean isLoginPostRequest = requestUri.equals(ApplicationApi.LOGIN) && httpMethod.equals(ApplicationApi.HTTP_POST);

        return isLoginPostRequest;
    }

}
