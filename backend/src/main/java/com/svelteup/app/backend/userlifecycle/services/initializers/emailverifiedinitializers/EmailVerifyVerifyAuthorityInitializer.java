package com.svelteup.app.backend.userlifecycle.services.initializers.emailverifiedinitializers;

import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.aws.ses.services.SEmailVerification;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.stereotype.Component;

@Component
public class EmailVerifyVerifyAuthorityInitializer extends EntityInitializer<EmailVerifyInitializerDto> {
    protected final SSvelteUpUser svelteUpUser;
    protected final SEmailVerification emailVerificationTokenService;

    public EmailVerifyVerifyAuthorityInitializer(SSvelteUpUser svelteUpUser, SEmailVerification emailVerificationTokenService) {
        super(false);
        this.svelteUpUser = svelteUpUser;
        this.emailVerificationTokenService = emailVerificationTokenService;
    }

    @Override
    public void initialize(EmailVerifyInitializerDto svelteUpEmailToken) {
        EmailVerificationToken discoveredToken = this.emailVerificationTokenService.findByEmailTokenAndTokenNotExpired(svelteUpEmailToken.emailToken);
        svelteUpEmailToken.securityContextUser =  this.svelteUpUser.grantEmailEstablishedAuthority(discoveredToken);
        svelteUpEmailToken.tokenEntity = discoveredToken;
    }
}
