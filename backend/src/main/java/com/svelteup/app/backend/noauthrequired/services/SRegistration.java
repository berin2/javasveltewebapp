package com.svelteup.app.backend.noauthrequired.services;

import com.svelteup.app.backend.aws.ses.enums.EmailTemplateEnum;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.aws.ses.services.SEmailVerification;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.profile.services.SSvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SUserContact;
import com.svelteup.app.backend.utils.services.interfaces.SThymeLeafTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.TreeMap;

@Service()
public class SRegistration implements  SThymeLeafTemplate, ApplicationEventPublisherAware {


    protected final SUserContact userContactService;
    protected final SSvelteUpUser svelteUpUserService;
    protected ApplicationEventPublisher applicationEventPublisher;
    protected final SEmailVerification emailVerificationService;
    protected final AuthenticationManager providerManager;
    protected final SSvelteUpUserProfile svelteUpUserProfile;

    public SRegistration(SUserContact userContactService, SSvelteUpUser svelteUpUserService, SEmailVerification emailVerificationService, AuthenticationManager providerManager, SSvelteUpUserProfile svelteUpUserProfile) {
        this.userContactService = userContactService;
        this.svelteUpUserService = svelteUpUserService;
        this.emailVerificationService = emailVerificationService;
        this.providerManager = providerManager;
        this.svelteUpUserProfile = svelteUpUserProfile;
    }


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public void post(UserRegisterDto postDto, HttpServletRequest request, HttpServletResponse response) {
        userContactService.existByEmail(postDto.email);  //throws 400 exception if email taken
        SvelteUpUser registeredUser = this.svelteUpUserService.initializeUser(postDto); //creates a new user
        EmailVerificationToken createdToken = this.emailVerificationService.initializeUser(postDto.username,postDto.email); //creeates user email token

        this.authenticateRegisteredUserSession(postDto.username, postDto.password,request);
        this.svelteUpUserProfile.initializeUser(registeredUser);

        Map<String,Object> thymeValues = new TreeMap<>();
        thymeValues.put("username",registeredUser.getUsername());
        thymeValues.put("emailToken",createdToken.getEmailToken());
        EmailSendEvent emailVerificationEvent = new EmailSendEvent(registeredUser,registeredUser.getUsername(),thymeValues, postDto.email, EmailTemplateEnum.VERIFICATION_TEMPLATE);
        this.applicationEventPublisher.publishEvent(emailVerificationEvent);
    }

    protected void authenticateRegisteredUserSession(String username,String password, HttpServletRequest request)
    {
        HttpSession requestSession = request.getSession(true);
        Authentication usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(username,password);
        usernameAndPasswordToken = providerManager.authenticate(usernameAndPasswordToken);
        SecurityContextHolder.getContext().setAuthentication(usernameAndPasswordToken);
        requestSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher  = applicationEventPublisher;
    }
}
