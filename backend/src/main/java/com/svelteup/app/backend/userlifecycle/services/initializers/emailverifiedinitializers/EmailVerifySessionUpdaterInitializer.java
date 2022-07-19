package com.svelteup.app.backend.userlifecycle.services.initializers.emailverifiedinitializers;

import com.svelteup.app.backend.security.events.UpdateSessionEvent;
import com.svelteup.app.backend.security.events.UpdateSessionType;
import com.svelteup.app.backend.security.services.SSessionUpdaterService;
import com.svelteup.app.backend.userlifecycle.dtos.EmailVerifyInitializerDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component()
public class EmailVerifySessionUpdaterInitializer  extends EntityInitializer<EmailVerifyInitializerDto> implements ApplicationEventPublisherAware {

    protected final SSessionUpdaterService sessionUpdaterService;
    protected ApplicationEventPublisher publisher;

    public EmailVerifySessionUpdaterInitializer(SSessionUpdaterService sessionUpdaterService) {
        super(false);
        this.sessionUpdaterService = sessionUpdaterService;
    }

    @Override
    public void initialize(EmailVerifyInitializerDto svelteUpEmailToken) {
        UpdateSessionEvent updateSessionEvent = new UpdateSessionEvent(svelteUpEmailToken.securityContextUser, svelteUpEmailToken.securityContextUser,UpdateSessionType.SYNC_SECURITY_CONTEXT_SESSION_OBJECTS_NOT_AUTHENTICATED);
        this.publisher.publishEvent(updateSessionEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
