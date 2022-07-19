package com.svelteup.app.backend.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.svelteup.app.backend.security.dtos.UsernamePasswordAuthenticationDto;
import com.svelteup.app.backend.security.services.UrlMatcherService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
* ApplicationUsernamePasswordLoginFilter is a custom login filter that processes a user login request
* with a username and password JSON request in the body.
* */
@Component
@RequiredArgsConstructor
public class ApplicationUsernamePasswordLoginFilter extends OncePerRequestFilter {
    private final UrlMatcherService urlMatcherService;
    private final ObjectMapper jacksonObjectMapper;
    private AuthenticationManager providerManager;

    @Autowired
    public void setProviderManager(AuthenticationManager injectedProviderManager)
    {
        this.providerManager = injectedProviderManager;
    }
    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {

        if(urlMatcherService.isLoginRequest(httpServletRequest) && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            try
            {
                String body = IOUtils.toString(httpServletRequest.getReader());
                UsernamePasswordAuthenticationDto usernameAndPasswordTokenDto = (UsernamePasswordAuthenticationDto) jacksonObjectMapper
                        .readValue(body,UsernamePasswordAuthenticationDto.class);
                Authentication usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(usernameAndPasswordTokenDto.username,usernameAndPasswordTokenDto.password);
                usernameAndPasswordToken = providerManager.authenticate(usernameAndPasswordToken); //try auth

                if(usernameAndPasswordToken.isAuthenticated()) {
                    if(httpServletRequest.getSession(false) != null)
                        httpServletRequest.getSession(false).invalidate();

                    SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken); // auth success set user
                }
                else
                    throw new UsernameNotFoundException("Failed to authenticate user with username and password combo provided.");
            }
            catch(AuthenticationException loginFailedException)
            {
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
