package com.svelteup.app.backend.profile.controller.email;

import com.svelteup.app.backend.aws.ses.enums.EmailTemplateEnum;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.aws.ses.services.SEmailVerification;
import com.svelteup.app.backend.aws.ses.services.StaticThymeMapBuilderService;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.REmailVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.transaction.NotSupportedException;
import java.util.Map;

@RequiredArgsConstructor
/**
 * IEmailControllerImpl is used to perform email verification related functions.
 */
public class IEmailControllerImpl implements IEmailController, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    protected  final REmailVerificationToken  emailVerificationTokenRepository;
    protected final SEmailVerification emailVerification;

    @Override
    public void resendVerificationEmail(SvelteUpUser user) throws Http400Exception, Http500Exception, NotSupportedException {
        EmailVerificationToken token = emailVerification.findBySurrogateIdWithCheck(user.getUsername(),user.getUsername());
        Map<String,Object> thymeTemplate = StaticThymeMapBuilderService.buildVerificationMap(user.getUsername(),token.getEmailToken());
        EmailSendEvent emailEvent = new EmailSendEvent(token,user.getUsername(),thymeTemplate,token.getEmailVerificationTokenEmail(), EmailTemplateEnum.VERIFICATION_TEMPLATE);

        this.applicationEventPublisher.publishEvent(emailEvent);
    }

    @Override
    public void updateVerificationTokenAndResendEmail(SvelteUpUser user, SvelteUpUserProfileDto dto) throws NotSupportedException {
        EmailVerificationToken token = emailVerification.findBySurrogateIdWithCheck(user.getUsername(),user.getUsername());
        token.refreshToken(dto.email);
        Map<String,Object> thymeTemplate = StaticThymeMapBuilderService.buildVerificationMap(user.getUsername(),token.getEmailToken());
        EmailSendEvent emailEvent = new EmailSendEvent(token,user.getUsername(),thymeTemplate, dto.email, EmailTemplateEnum.VERIFICATION_TEMPLATE);
        this.applicationEventPublisher.publishEvent(emailEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
