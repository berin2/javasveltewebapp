package com.svelteup.app.backend.profile.controller.account;

import com.svelteup.app.backend.aws.ses.enums.EmailTemplateEnum;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.aws.ses.services.SEmailVerification;
import com.svelteup.app.backend.aws.ses.services.StaticThymeMapBuilderService;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.profile.dtos.ContactDto;
import com.svelteup.app.backend.profile.dtos.DeleteAccountDto;
import com.svelteup.app.backend.profile.dtos.PasswordChangeDto;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SSvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SUserContact;
import com.svelteup.app.backend.security.events.UpdateSessionEvent;
import com.svelteup.app.backend.security.events.UpdateSessionType;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.REmailVerificationToken;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import com.svelteup.app.backend.security.services.SSessionUpdaterService;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller()
@RequiredArgsConstructor
public class IAccountControllerImpl extends SHttpExceptionThrower implements IAccountController, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    protected final REmailVerificationToken emailVerificationTokenRepository;
    protected final SEmailVerification emailVerification;
    protected final RSvelteUpUser rSvelteUpUser;
    protected final PasswordEncoder environmentEncoder;
    protected final SSessionUpdaterService sessionUpdaterService;
    protected final SUserContact sUserContact;
    protected final SSvelteUpUserProfile sSvelteUpUserProfile;


    /**
     * Updates a phone number and sends a verification code text to the registered phone number.
     *
     * @param profile           auth user
     * @param passwordChangeDto contains passwordChangeInfo
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void putPassword(SvelteUpUser profile, PasswordChangeDto passwordChangeDto) throws Http401Exception, Http403Exception, Http500Exception {
        SvelteUpUser discoveredUser = this.rSvelteUpUser.getById(profile.getUsername());

        if(environmentEncoder.matches(passwordChangeDto.oldPassword, discoveredUser.getPassword()))
        {
            discoveredUser.setPassword(passwordChangeDto.newPassword);
            UpdateSessionEvent updateSessionEvent = new UpdateSessionEvent(discoveredUser,discoveredUser, UpdateSessionType.SYNC_SECURITY_CONTEXT_SESSION_OBJECTS_AUTHENTICATED);
            this.applicationEventPublisher.publishEvent(updateSessionEvent);
        }
        else
            this.throwHttp400("putPassword",this.getClass().toString(),profile.getUsername(),passwordChangeDto);


    }

    /**
     * Updates a phone number and sends a verification code text to the registered phone number.
     *
     * @param profile    auth user
     * @param accountDto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void putPhoneNumber(SvelteUpUser profile, SvelteUpUserProfileDto accountDto) throws Http401Exception, Http403Exception, Http500Exception {
    }

    /**
     * Deletes Account and sends a verification code text to the registered email.
     *
     * @param profile       auth user
     * @param deleteAccount contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void deleteAccount(SvelteUpUser profile, DeleteAccountDto deleteAccount) throws Http401Exception, Http403Exception, Http500Exception {

    }

    /**
     * Updates email and sends a verification code text to the registered email.
     *
     * @param profile auth user
     * @param dto     contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void putFoe(SvelteUpUser profile, ContactDto dto) throws Http401Exception, Http403Exception, Http500Exception, NotSupportedException {
            this.sUserContact.addBlockedContact(profile.getUsername(),dto);
    }

    /**
     * Updates email and sends a verification code text to the registered email.
     *
     * @param profile auth user
     * @param dtoList     contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void deleteFoe(SvelteUpUser profile, List<ContactDto> dtoList) throws Http401Exception, Http403Exception, Http500Exception, NotSupportedException {

        for(ContactDto dto: dtoList)
            if(dto != null)
                this.sUserContact.deleteBlockedContact(profile.getUsername(),dto);
    }

    /**
     * Updates email and sends a verification code text to the registered email.
     *
     * @param profile    auth user
     * @param accountDto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void putTwoFactorAuth(SvelteUpUser profile, SvelteUpUserProfileDto accountDto) throws Http401Exception, Http403Exception, Http500Exception {

    }

    /**
     * Updates email and sends a verification code text to the registered email.
     *
     * @param profile    auth user
     * @param accountDto contains phone num info
     * @throws Http401Exception
     * @throws Http403Exception
     * @throws Http500Exception
     */
    @Override
    public void putEmail(SvelteUpUser profile, SvelteUpUserProfileDto accountDto) throws Http401Exception, Http403Exception, Http500Exception, IOException, NotSupportedException {
       updateVerificationTokenAndResendEmail(profile,accountDto);
    }

    @Override
    public void verifyEmail(UUID emailToken) throws NotSupportedException {
        EmailVerificationToken emailVerificationToken = this.emailVerificationTokenRepository.findByEmailToken(emailToken);
        if(emailVerificationToken != null && emailVerificationToken.getOwningUsername() != null)
        {
            SvelteUpUserProfile profile = this.sSvelteUpUserProfile.findById(emailVerificationToken.getOwningUsername());
            profile.setEmail(emailVerificationToken.getEmailVerificationTokenEmail());
            this.sSvelteUpUserProfile.save(profile);
        }
    }

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
