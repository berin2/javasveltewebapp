package com.svelteup.app.backend.userlifecycle.services.initializerchains;

import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.emailverifiedinitializers.EmailVerifyGrantFullAuthorityCheckerInitiailizer;
import com.svelteup.app.backend.userlifecycle.services.initializers.emailverifiedinitializers.EmailVerifySessionUpdaterInitializer;
import com.svelteup.app.backend.userlifecycle.services.initializers.emailverifiedinitializers.EmailVerifyVerifyAuthorityInitializer;
import org.springframework.stereotype.Component;

@Component
public class VerifyEmailInitializerChain extends EntityInitializerChain<EmailVerifyInitializerDto>{
    protected final Integer EMAIL_VERIFIED_INITIALIZER_INDEX = 0;
    protected final Integer REFRESH_SESSION_INITIALIZER_INDEX = 1;
    protected final Integer GRANT_FULL_AUTHORITY_INIT_INDEX = 2;

    public VerifyEmailInitializerChain(
            EmailVerifyVerifyAuthorityInitializer emailVerifyInitializer,
            EmailVerifySessionUpdaterInitializer sessionUpdaterInitializer,
            EmailVerifyGrantFullAuthorityCheckerInitiailizer emailVerifyGrantFullAuthorityCheckerInitiailizer
    )
    {
        this.addToInitializeChain(EMAIL_VERIFIED_INITIALIZER_INDEX,emailVerifyInitializer);
        this.addToInitializeChain(REFRESH_SESSION_INITIALIZER_INDEX,sessionUpdaterInitializer);
        this.addToInitializeChain(GRANT_FULL_AUTHORITY_INIT_INDEX, emailVerifyGrantFullAuthorityCheckerInitiailizer);
    }
}
