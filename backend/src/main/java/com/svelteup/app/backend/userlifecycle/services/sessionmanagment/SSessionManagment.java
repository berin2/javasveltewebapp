package com.svelteup.app.backend.userlifecycle.services.sessionmanagment;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SSessionManagment {

    SecurityContextRepository scp;

    @PostConstruct()
    public void test()
    {
        SecurityContextPersistenceFilter spf;
    }
    public SvelteUpUser getSessionSvelteUpUser()
    {
        HttpSession redisSession = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
        SecurityContext context = (SecurityContext) redisSession.getAttribute("SPRING_SECURITY_CONTEXT");
        SvelteUpUser sessionUser = (SvelteUpUser) context.getAuthentication().getPrincipal();
       return sessionUser;
    }

    public void updateSessionUser(SvelteUpUser updatedUser)
    {
        SvelteUpUser discoveredUser = this.getSessionSvelteUpUser();
    }
}
