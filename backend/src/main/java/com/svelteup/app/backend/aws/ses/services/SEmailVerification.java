package com.svelteup.app.backend.aws.ses.services;

import com.svelteup.app.backend.aop.aspects.owninguserpk.OwningUserPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.aop.aspects.owninguserpk.SurrogateOwningUserPkAccessChecker;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http404Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserPk;
import com.svelteup.app.backend.security.repositories.REmailVerificationToken;
import com.svelteup.app.backend.security.services.interfaces.IEmailVerification;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import com.svelteup.app.backend.utils.services.interfaces.IUserLifeCycle;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
public class SEmailVerification extends SSurrogateEntityOwningUserPk<EmailVerificationToken>
        implements IUserLifeCycle<EmailVerificationToken,EmailSendEvent>, IEmailVerification, ApplicationEventPublisherAware
{

    protected  REmailVerificationToken emailVerificationTokenRepository;
    protected  ApplicationEventPublisher applicationEventPublisher;

    public SEmailVerification(REmailVerificationToken emailVerificationTokenRepository) {
        super(emailVerificationTokenRepository);
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    /**
     * initializeVerifiedUser creates an EmailVerificationToken token for a registered user and sends
     * an initial email event
     * @param emailVerificationEvent the event to publish.
     */
    @Override
    public EmailVerificationToken initializeUser(EmailSendEvent emailVerificationEvent) {
        String username = emailVerificationEvent.getOwningUsername();
        String email = emailVerificationEvent.getEmailDestination();
        EmailVerificationToken createdToken = new EmailVerificationToken(username,email);
        return this.emailVerificationTokenRepository.save(createdToken);
    }

    public EmailVerificationToken initializeUser(String username,String email) {
        EmailVerificationToken createdToken = new EmailVerificationToken(username,email);
        return this.emailVerificationTokenRepository.save(createdToken);
    }

    @Override
    public void destroyVerifiedUser(String username) {

    }


    /**
     * This endpoint  is used to verify a user email.
     *
     * @param emailVerificationToken The UUID token sent to the email address.
     * @throws Http500Exception If the uuid token does not exist in the verification db.
     * @throws Http403Exception If the uuid token is expired. UUID tokens have a 24 hour  life span.
     */
    @Override
    public void get(UUID emailVerificationToken) throws Http404Exception,Http403Exception, NotSupportedException {
        EmailVerificationToken discoveredToken = this.findBySurrogateId(emailVerificationToken);
        EmailSendEvent  emailVerificationEvent = null;
        long tokenExpirationDate = discoveredToken.getEmailVerificationTokenExpiry();

        if(System.currentTimeMillis() < tokenExpirationDate)
        {
            //emailVerificationEvent =  new EmailVerificationEvent(discoveredToken, discoveredToken.getOwningUsername(),discoveredToken.getEmailVerificationTokenEmail());
            //this.applicationEventPublisher.publishEvent(emailVerificationEvent);
        }
        else
            this.throwHttp403("get", this.getClass().toString(),discoveredToken.getOwningUsername()," The email verification token has expired.");

    }

    /**
     * put is used to init a new email verification token for a user.
     *
     * @param username the authenticated user to initiate a email verification request.
     * @throws Http500Exception Throws 500 if the authenticated user does not have a currect email verification token.
     */
    @Override
    public void put(String username) throws Http500Exception, NotSupportedException {
        EmailVerificationToken discoveredToken = this.findById(username);
        discoveredToken.refreshToken();
    }

    /**
     * putEmailVerificationTokenEmail is used to update a verification token  to a new email address.
     * @param username  the autheneticated user
     * @param emailToUpdateTo the new email to update and dispatch an email to.
     * @return updated  token
     * @throws NotSupportedException if the methodis not supported
     */
    public EmailVerificationToken putEmailVerificationTokenEmail(String username,String emailToUpdateTo) throws NotSupportedException {
        EmailVerificationToken discoveredToken = this.findBySurrogateIdWithCheck(username,username);
        discoveredToken.refreshToken(emailToUpdateTo);
        return discoveredToken;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public EmailVerificationToken findByEmailToken(UUID emailToken)
    {
        return this.emailVerificationTokenRepository.findByEmailToken(emailToken);
    }

    public EmailVerificationToken findByEmailTokenAndTokenNotExpired(UUID emailToken) throws Http400Exception
    {
        EmailVerificationToken discoveredToken = this.findByEmailToken(emailToken);
        if(discoveredToken.getEmailVerificationTokenExpiry() <  System.currentTimeMillis())
            this.throwHttp400("findByEmailTokenAndTokenNotExpired",this.getClass().toString(),"SYSTEM",emailToken," EmailToken was expired but was expected not to be expired.");

        return discoveredToken;
    }
}
