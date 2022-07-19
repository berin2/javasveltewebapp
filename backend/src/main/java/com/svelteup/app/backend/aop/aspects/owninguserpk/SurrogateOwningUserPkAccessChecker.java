package com.svelteup.app.backend.aop.aspects.owninguserpk;

import com.svelteup.app.backend.aop.aspects.AopBase;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class SurrogateOwningUserPkAccessChecker extends AopBase {
    public static final String NO_OBJECT_ACCESS_PERMISSION = "User %s attempted to access object with surrogateID %s but was not the owning user of said object.";

    /**
     * Executes after afterReturningOwningUserNonPrimaryKeyPermissionCheck  returns an instance of OwningUserNonPrimaryKeySurrogateEntity.
     * @param joinPoin The method JoinPoint containing the method arguments of afterReturningOwningUserPrimaryKeyPermissionCheck
     * @param owningUserPrimaryKeySurrogateEntity the returned OwningUserPrimaryKeySurrogateEntity
     * @return returns the param owningUserNonPrimaryKeySurrogateEntity.
     * @throws Http403Exception Throws Http403Exception when the user is not the owning user of the entity.
     */
    @AfterReturning(
            returning = "owningUserPrimaryKeySurrogateEntity",
            pointcut="execution(*  *.afterReturningOwningUserPrimaryKeyPermissionCheck(..))"
    )
    public OwningUserPrimaryKeySurrogateEntity owningUserPairedNonPrimaryKeyPermissionCheck(JoinPoint joinPoin, OwningUserPrimaryKeySurrogateEntity owningUserPrimaryKeySurrogateEntity) throws Http403Exception {
        this.nullObjectCheck(owningUserPrimaryKeySurrogateEntity,"null OwningUserPrimaryKeySurrogateEntity passed to method.",SurrogateOwningUserPkAccessChecker.class);
        this.objectArgsCheck(joinPoin,2,"owningUserPairedNonPrimaryKeyPermissionCheck",this.getClass().toString());
        String authenticatedUsername = (String) joinPoin.getArgs()[0];
        Object objectUUID = owningUserPrimaryKeySurrogateEntity.getSurrogateId();

        if(!authenticatedUsername.equals(owningUserPrimaryKeySurrogateEntity.getOwningUsername()))
            this.throwHttp403("accessCheck",joinPoin.getClass().getName(),authenticatedUsername,objectUUID);

        return owningUserPrimaryKeySurrogateEntity;
    }

    /**
     * Performs access check on OwningUserNonPrimaryKeySurrogateEntity before entity access.
     * @param joinPoin the JoinPoint which contains the neccessary
     * @return OwningUserNonPrimaryKeySurrogateEntity passed into the beforeOwningUserNonPrimaryKeyPermissionCheck.
     * @throws Http403Exception Throws 403 exception when user cannot access the entity.
     */
    @Before("execution(*  *.beforeOwningUserPrimaryKeyPermissionCheck(..))")
    public OwningUserPrimaryKeySurrogateEntity beforeOwningUserNonPrimaryKeySurrogateEntity(JoinPoint joinPoin) throws Http403Exception {
        OwningUserPrimaryKeySurrogateEntity owningUserNonPrimaryKeySurrogateEntity =(OwningUserPrimaryKeySurrogateEntity) joinPoin.getArgs()[1];
        this.nullObjectCheck(owningUserNonPrimaryKeySurrogateEntity,"null OwningUserPrimaryKeySurrogateEntity passed to method.",SurrogateOwningUserPkAccessChecker.class);
        this.objectArgsCheck(joinPoin,2,"beforeOwningUserNonPrimaryKeySurrogateEntity",this.getClass().toString());
        this.owningUserPairedNonPrimaryKeyPermissionCheck(joinPoin,owningUserNonPrimaryKeySurrogateEntity);
        return owningUserNonPrimaryKeySurrogateEntity;
    }


}
