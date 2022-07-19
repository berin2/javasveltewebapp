package com.svelteup.app.backend.userlifecycle.services.initializerchains;

import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.registrationinitializers.EmailVerificationTokenInitializer;
import com.svelteup.app.backend.userlifecycle.services.initializers.registrationinitializers.HttpSessionRegistrationInitializer;
import com.svelteup.app.backend.userlifecycle.services.initializers.registrationinitializers.SvelteUpUserInitializer;
import org.springframework.stereotype.Component;

@Component
public class RegistrationInitializerChain extends EntityInitializerChain<UserRegisterDto>{

    protected static final Integer SVELTEUP_USER_INIT_ORDER = 0;
    protected static final Integer SVELTEUP_EMAIL_VERIFICATION_TOKEN_INIT_ORDER = 1;
    protected static final Integer SVELTEUP_HTTP_SESSION_INIT_ORDER = 2;

    public RegistrationInitializerChain(
            SvelteUpUserInitializer userInitializer,
            EmailVerificationTokenInitializer emailInitializer,
            HttpSessionRegistrationInitializer httpSessionRegistrationInitializer
    )
    {
        this.addToInitializeChain(SVELTEUP_USER_INIT_ORDER,userInitializer);
        this.addToInitializeChain(SVELTEUP_EMAIL_VERIFICATION_TOKEN_INIT_ORDER,emailInitializer);
        this.addToInitializeChain(SVELTEUP_HTTP_SESSION_INIT_ORDER,httpSessionRegistrationInitializer);
    }

}
