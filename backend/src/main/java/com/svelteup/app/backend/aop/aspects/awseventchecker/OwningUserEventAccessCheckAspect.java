package com.svelteup.app.backend.aop.aspects.awseventchecker;

import com.svelteup.app.backend.aop.aspects.AopBase;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.events.OwningUserEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect()
@Component
public class OwningUserEventAccessCheckAspect extends AopBase {

    public OwningUserEventAccessCheckAspect()
    {
    }

    @Before(
                    "execution(* com.svelteup.app.backend.aws.*.put(..)) || " +
                    "execution(* com.svelteup.app.backend.aws.*.post(..))" +
                    "execution(* com.svelteup.app.backend.aws.*.delete(..))"
    )
    public void beforeAccessCheck(JoinPoint joinPoint)
    {
        this.objectEventArgsCheck(joinPoint,1,"beforeAccessCheck",this.getClass().getName());
        SvelteUpUser authenticatedUser =(SvelteUpUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authenticatedUser.getUsername();
        OwningUserEvent extractedEvent = (OwningUserEvent) joinPoint.getArgs()[0];

        if(!authenticatedUser.getUsername().equals(extractedEvent.getOwningUsername()))
            this.throwHttp403("accessCheck",joinPoint.getClass().getName(),username,"Presented OwningUserEvent did not have matching username in the SecurityContext  to the one passed to the joinPoin.");

    }
}
