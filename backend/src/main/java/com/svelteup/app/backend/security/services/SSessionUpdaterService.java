package com.svelteup.app.backend.security.services;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.security.events.UpdateSessionEvent;
import com.svelteup.app.backend.security.filters.SessionRefresherFilter;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class SSessionUpdaterService extends SHttpExceptionThrower implements ApplicationListener<UpdateSessionEvent> {
    protected final SSvelteUpUser svelteUpUser;
    protected final RedisIndexedSessionRepository sessionRepository;
    SpringSessionBackedSessionRegistry registry;

    @Override
    public void onApplicationEvent(UpdateSessionEvent event) {
        switch (event.updateType){
            case SYNC_SECURITY_CONTEXT_SESSION_OBJECTS_AUTHENTICATED:
                this.updateAllAuthoritiesInSessionContextWithAuthenticatedUser(event);
                break;
            case SYNC_SECURITY_CONTEXT_SESSION_OBJECTS_NOT_AUTHENTICATED:
                this.updateAllAuthoritiesInSessionContextWithUnAuthenticatedUser(event);
            default:
                break;
        }
    }

    private  void  destroyAllSessions(String username)
    {

    }

    private void updateAllAuthoritiesInSessionContextWithUnAuthenticatedUser(UpdateSessionEvent event)
    {
        SvelteUpUser refreshedUserObject = event.userWithUpdatedSessionValues;
        SecurityContext redisContext = null;
        SvelteUpUser userFromRedisSession =  null;

        Authentication updatedSecurityContextAuthentication  = new UsernamePasswordAuthenticationToken(refreshedUserObject,null,refreshedUserObject.getAuthorities());

        for(Session redisSession: sessionRepository.findByPrincipalName(refreshedUserObject.getUsername()).values())
        {
            redisContext = ((SecurityContext) redisSession.getAttribute("SPRING_SECURITY_CONTEXT"));
            userFromRedisSession = (SvelteUpUser) redisContext.getAuthentication().getPrincipal();

            if(userFromRedisSession  != null && userFromRedisSession.getUsername() != null)
            {
                if(userFromRedisSession.getUsername().equals(refreshedUserObject.getUsername()) )
                {
                     redisSession.setAttribute(SessionRefresherFilter.REFRESH_SECURITY_CONTEXT_PRINCIPAL,true);
                }
                else
                    throwHttp403("initialize",this.getClass().toString(), redisSession.getId(),String.format("Authenticated user %s did not own session with id %s  which contained an owning user of %s",refreshedUserObject.getUsername(),redisSession.getId(),refreshedUserObject.getUsername()));
            }
            else
                throwHttp500("initialize",this.getClass().toString(), redisSession.getId(),"Redis session was bound to an empty user object or Redis session user object but username is null  or empty.");

        }
    }
    private void updateAllAuthoritiesInSessionContextWithAuthenticatedUser(UpdateSessionEvent event)
    {
        Authentication updatedSecurityContextAuthentication  = new UsernamePasswordAuthenticationToken(event.userWithUpdatedSessionValues,null,event.userWithUpdatedSessionValues.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedSecurityContextAuthentication);
        this.updateAllAuthoritiesInSessionContextWithUnAuthenticatedUser(event);
    }


    protected Session getSessionFromHttpServletRequest(String authenticatedUser) throws Http500Exception
    {
        Session authenticatedSession = (Session) ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);

        if(authenticatedSession != null && authenticatedSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) == null)
            this.throwHttp500("getSessionFromHttpServletRequest",this.getClass().toString(),authenticatedUser,"System request to perform a session update operation but the session was null or the spring security context request attribute was null.");

        return authenticatedSession;
    }
}
