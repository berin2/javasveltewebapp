package com.svelteup.app.backend.userlifecycle.services.initializers.registrationinitializers;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.services.initializerchains.EntityInitializerChain;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.apache.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class HttpSessionRegistrationInitializer extends EntityInitializer<UserRegisterDto> {
    protected final SSvelteUpUser sSvelteUpUser;
    protected final AuthenticationManager  providerManager;


    public HttpSessionRegistrationInitializer(SSvelteUpUser sSvelteUpUser, AuthenticationManager providerManager) {
        super(false);
        this.sSvelteUpUser = sSvelteUpUser;
        this.providerManager = providerManager;
    }

    @Override
    public void initialize(UserRegisterDto registerDto) throws ServletException, IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpSession requestSession = request.getSession(true);

        Authentication usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(registerDto.username,registerDto.password);
        usernameAndPasswordToken = providerManager.authenticate(usernameAndPasswordToken);
        SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken);
        requestSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        request.getRequestDispatcher(ApplicationApi.LOGIN).forward(request,response);
    }
}
