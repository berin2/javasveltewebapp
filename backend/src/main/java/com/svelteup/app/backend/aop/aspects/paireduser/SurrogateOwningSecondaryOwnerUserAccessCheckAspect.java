package com.svelteup.app.backend.aop.aspects.paireduser;

import com.svelteup.app.backend.aop.aspects.AopBase;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect()
@Component()
public class SurrogateOwningSecondaryOwnerUserAccessCheckAspect extends AopBase {

    public static final String NO_OBJECT_ACCESS_PERMISSION = "User %s attempted to access object with surrogateID %s but was not the owning user of said object.";
    public static final String NULL_AUTHENTICATED_USERNAME = "An authenticated user whose username was null attempted to acces object with surrogateId %s.";
    public static final String INVALID_ARGS_SUPPLIED_TO_JOINPOIN = "Invalid argument passed to JoinPoint owningUserNonPkEntityAccessCheck with JoinPoint value: %s .";

    @Before("execution(* *.beforeOwningUserPairedNonPrimaryKeyPermissionCheck(..))")
    public void beforeOwningUserPairedNonPrimaryKeyPermissionCheck(JoinPoint joinPoin)
    {
        this.objectArgsCheck(joinPoin,2,"beforeOwningUserPairedNonPrimaryKeyPermissionCheck",this.getClass().toString());
    }

    @Before("execution(* *.beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(..))")
    public void beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(JoinPoint  joinPoin)
    {
        this.objectArgsCheck(joinPoin,2,"beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck",this.getClass().toString());
        String username = joinPoin.getArgs()[0].toString();
        PairedUserNonPrimaryKeyEntity pairedEntity =(PairedUserNonPrimaryKeyEntity) joinPoin.getArgs()[1];

        if(!username.equals(pairedEntity.getOwningUsername()))
            this.throwHttp403("beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck",this.getClass().toString(),username, pairedEntity);
    }

    @Before("execution(* *.beforeOwningUserPairedNonPrimaryKeyIsSecondareyOwningUserCheck(..))")
    public void beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck(JoinPoint joinPoin)
    {
        this.objectArgsCheck(joinPoin,2,"beforeOwningUserPairedNonPrimaryKeyIsSecondareyOwningUserCheck",this.getClass().toString());
        String username = joinPoin.getArgs()[0].toString();
        PairedUserNonPrimaryKeyEntity pairedEntity =(PairedUserNonPrimaryKeyEntity) joinPoin.getArgs()[1];

        if(!username.equals(pairedEntity.getSecondaryOwningUsername()))
            this.throwHttp403("beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck",this.getClass().toString(),username, pairedEntity);
    }

    @Before("execution(* *.beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(..))")
    public void beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(JoinPoint joinPoin)
    {
        this.objectArgsCheck(joinPoin,2,"beforeOwningUserPairedNonPrimaryKeyIsSecondareyOwningUserCheck",this.getClass().toString());
        String username = joinPoin.getArgs()[0].toString();
        PairedUserNonPrimaryKeyEntity pairedEntity =(PairedUserNonPrimaryKeyEntity) joinPoin.getArgs()[1];

        if(username.equals(pairedEntity.getSecondaryOwningUsername()) && username.equals(pairedEntity.getOwningUsername()))
            this.throwHttp403("beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck",this.getClass().toString(),username, pairedEntity);
    }

    @AfterReturning(
            returning = "owningUserPkEntity",
            pointcut = "execution(* *.afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(..)))"
    )
    public void  owningUserPkAccessCheck(JoinPoint joinPoin, OwningUserNonPrimaryKeySurrogateEntity owningUserPkEntity)
    {
        this.objectArgsCheck(joinPoin,2,"owningUserPkAccessCheck",this.getClass().toString());
        accessCheck(joinPoin,owningUserPkEntity);
    }

    @AfterReturning(
            returning = "owningUserPairedPkEntity",
            pointcut = "execution(* afterReturningIsOwningUserOrSecondaryUserCheck(..))"
    )
    public void afterReturningIsOwningUserOrSecondaryUserCheck(JoinPoint joinPoin, OwningUserNonPrimaryKeySurrogateEntity owningUserPairedPkEntity)
    {
        this.objectArgsCheck(joinPoin,2,"owningUserPkAccessCheck",this.getClass().toString());
        String username = joinPoin.getArgs()[0].toString();
        PairedUserNonPrimaryKeyEntity pairedEntity =(PairedUserNonPrimaryKeyEntity) joinPoin.getArgs()[1];

        if(!pairedEntity.getOwningUsername().equals(username) && !pairedEntity.getSecondaryOwningUsername().equals(username))
            this.throwHttp403("afterReturningIsOwningUserOrSecondaryUserCheck",this.getClass().toString(),username,owningUserPairedPkEntity.getSurrogateId());
    }

    @AfterReturning(
            returning = "owningUserPairedEntity",
            pointcut = "execution(* afterReturningSecondaryOwningUserNonPrimaryKeyPermissionCheck(..))"
    )
    public void afterReturningIsSecondaryUserCheck(JoinPoint joinPoin, OwningUserNonPrimaryKeySurrogateEntity owningUserPairedEntity)
    {
        this.objectArgsCheck(joinPoin,2,"owningUserPkAccessCheck",this.getClass().toString());
        String username = joinPoin.getArgs()[0].toString();
        PairedUserNonPrimaryKeyEntity pairedEntity =(PairedUserNonPrimaryKeyEntity) joinPoin.getArgs()[1];

        if(!pairedEntity.getSecondaryOwningUsername().equals(username))
            this.throwHttp403("afterReturningIsOwningUserOrSecondaryUserCheck",this.getClass().toString(),username,owningUserPairedEntity.getSurrogateId());
    }

    public void accessCheck(JoinPoint joinPoin, OwningUserNonPrimaryKeySurrogateEntity owningUserNonPKEntity) throws Http403Exception
    {
        this.objectArgsCheck(joinPoin,2,"accessCheck",this.getClass().toString());
        String authenticatedUsername = joinPoin.getArgs()[0].toString();
        UUID   objectUUID = UUID.fromString(joinPoin.getArgs()[1].toString());
        String owningUsername = owningUserNonPKEntity.getOwningUsername();

        if(!authenticatedUsername.equals(owningUsername))
            this.throwHttp403("accessCheck",joinPoin.getClass().getName(),authenticatedUsername,objectUUID);
    }
}
