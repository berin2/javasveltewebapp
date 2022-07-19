package com.svelteup.app.backend.userlifecycle.services.initializers.emailverifiedinitializers;

import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.events.GrantFullyAuthorizedAuthorityEvent;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EmailVerifyGrantFullAuthorityCheckerInitiailizer extends EntityInitializer<EmailVerifyInitializerDto> implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    public EmailVerifyGrantFullAuthorityCheckerInitiailizer() {
        super(false);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void initialize(EmailVerifyInitializerDto svelteUpEmailToken) {

        if(svelteUpEmailToken.securityContextUser.getIsIdentityValidated() && svelteUpEmailToken.securityContextUser.getIsEmailValidated())
        {
            GrantFullyAuthorizedAuthorityEvent event = new GrantFullyAuthorizedAuthorityEvent(svelteUpEmailToken.securityContextUser);
            this.eventPublisher.publishEvent(event);
        }
    }
}
