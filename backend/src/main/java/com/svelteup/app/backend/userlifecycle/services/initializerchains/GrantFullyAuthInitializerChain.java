package com.svelteup.app.backend.userlifecycle.services.initializerchains;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.security.events.UpdateSessionEvent;
import com.svelteup.app.backend.security.events.UpdateSessionType;
import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.events.GrantFullyAuthorizedAuthorityEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrantFullyAuthInitializerChain extends EntityInitializerChain<SvelteUpUser> implements ApplicationListener<GrantFullyAuthorizedAuthorityEvent>, ApplicationEventPublisherAware
{
    protected final SSvelteUpUser svelteUpUser;
    protected ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void onApplicationEvent(GrantFullyAuthorizedAuthorityEvent event) throws Http500Exception {
        SvelteUpUser eventUser = event.getOwningSvelteUpUserObject();
        this.ensureUserIsCompletelyValidated(eventUser);
        eventUser = this.svelteUpUser.grantFullySetupUserAuthority(event.getOwningSvelteUpUserObject());
        UpdateSessionEvent updateSessionEvent = new UpdateSessionEvent(eventUser,eventUser, UpdateSessionType.SYNC_SECURITY_CONTEXT_SESSION_OBJECTS_AUTHENTICATED);
        this.applicationEventPublisher.publishEvent(updateSessionEvent);
    }


    protected void ensureUserIsCompletelyValidated(SvelteUpUser user) throws Http500Exception
    {
        boolean hasNotFullySetup = false;
        boolean identityAndEmailFlagsSet = user.getIsEmailValidated() && user.getIsIdentityValidated();

        for(AccountAuthority accountAuthority : user.getAccountAuthorityList())
        {
            String authorityString = accountAuthority.getAuthority();
            if(authorityString != null)
                if(authorityString.equals(Authority.NOT_FULLY_SETUP_ACCOUNT))
                    hasNotFullySetup =  true;
        }

        if(!hasNotFullySetup && !identityAndEmailFlagsSet)
            this.throwHttp500("ensureUserIsCompletelyValidated",this.getClass().toString(),user.getUsername(),"Application Attempted to give fully authenticated authority to the user but the user was not fully authenticated.");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
