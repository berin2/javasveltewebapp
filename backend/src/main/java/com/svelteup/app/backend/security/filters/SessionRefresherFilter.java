package com.svelteup.app.backend.security.filters;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.services.AccountDetailsService;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@Component()
@RequiredArgsConstructor()
public class SessionRefresherFilter extends HttpFilter {


    public static String REFRESH_SECURITY_CONTEXT_PRINCIPAL = "REPLACE_SESSION";
    protected final AccountDetailsService svelteUpUserService;
    protected final SSvelteUpUser svelteUpUser;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        SvelteUpUser securityContextPrincipal;

        if(session != null && session.getAttribute(REFRESH_SECURITY_CONTEXT_PRINCIPAL) != null)
        {
            securityContextPrincipal = (SvelteUpUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            securityContextPrincipal = svelteUpUser.findByUsername(securityContextPrincipal.getUsername());
            Authentication replacementAuth = new UsernamePasswordAuthenticationToken(securityContextPrincipal, null, securityContextPrincipal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(replacementAuth);
            session.setAttribute(REFRESH_SECURITY_CONTEXT_PRINCIPAL,null);
        }

        chain.doFilter(request,response);
    }
    }
