package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.svelteup.app.backend.security.events.UpdateSessionEvent;
import com.svelteup.app.backend.security.events.UpdateSessionType;
import com.svelteup.app.backend.security.services.SSessionUpdaterService;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.transaction.NotSupportedException;
import java.io.IOException;

@Component()
public class IdentityEstablishedRefreshUserStoreInitializer extends EntityInitializer<EstablishCustomerIdentityDto> implements ApplicationEventPublisherAware {

    protected RedisIndexedSessionRepository sessionRepository;
    protected SSvelteUpUser svelteUpUserService;
    protected SSessionUpdaterService  sessionUpdaterService;
    protected ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    public IdentityEstablishedRefreshUserStoreInitializer(RedisIndexedSessionRepository redisBabbby, SSvelteUpUser svelteUpUser) {
        super(false);
        this.sessionRepository = redisBabbby;
        this.svelteUpUserService = svelteUpUser;
    }

    public IdentityEstablishedRefreshUserStoreInitializer()
    {
        super(false);
    }

    @Override
    public void initialize(EstablishCustomerIdentityDto identityDto) throws NotSupportedException, ServletException, IOException
    {
        UpdateSessionEvent updateSessionEvent = new UpdateSessionEvent(identityDto.user,identityDto.user, UpdateSessionType.SYNC_SECURITY_CONTEXT_SESSION_OBJECTS_AUTHENTICATED);
        this.applicationEventPublisher.publishEvent(updateSessionEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
