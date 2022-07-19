package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.events.GrantFullyAuthorizedAuthorityEvent;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class IdentityEstablishedGrantFullAuthorityInitializer extends EntityInitializer<EstablishCustomerIdentityDto> implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    public IdentityEstablishedGrantFullAuthorityInitializer() {
        super(false);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void initialize(EstablishCustomerIdentityDto registeredUserToken) {

        if(registeredUserToken.user.getIsIdentityValidated() && registeredUserToken.user.getIsEmailValidated())
        {
            GrantFullyAuthorizedAuthorityEvent event = new GrantFullyAuthorizedAuthorityEvent(registeredUserToken.user);
            this.eventPublisher.publishEvent(event);
        }
    }
}