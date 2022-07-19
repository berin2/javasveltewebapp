package com.svelteup.app.backend.userlifecycle.services.initializers.registrationinitializers;

import com.svelteup.app.backend.aws.ses.enums.EmailTemplateEnum;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.aws.ses.services.SEmailSender;
import com.svelteup.app.backend.aws.ses.services.SEmailVerification;
import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

@Component
public class EmailVerificationTokenInitializer extends EntityInitializer<UserRegisterDto> implements ApplicationEventPublisherAware {
    protected SEmailVerification emailVerificationService;
    protected SEmailSender emailSender;
    protected ApplicationEventPublisher applicationEventPublisher;

    public EmailVerificationTokenInitializer(SEmailVerification emailVerification) {
        super(false);
        this.emailVerificationService = emailVerification;
    }

    @Override
    public void initialize(UserRegisterDto dto)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        EmailVerificationToken createdToken = emailVerificationService.initializeUser(dto.username,dto.email);
        Map<String,Object> verifactionMap = new TreeMap<>();
        verifactionMap.put("username",dto.username);
        verifactionMap.put("emailToken",createdToken.getEmailToken());
        EmailSendEvent sendEvent = new EmailSendEvent(dto,dto.username,verifactionMap, dto.email, EmailTemplateEnum.VERIFICATION_TEMPLATE);
        this.applicationEventPublisher.publishEvent(sendEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
