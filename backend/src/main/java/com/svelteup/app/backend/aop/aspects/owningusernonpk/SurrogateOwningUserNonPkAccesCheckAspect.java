package com.svelteup.app.backend.aop.aspects.owningusernonpk;

import com.svelteup.app.backend.aop.aspects.AopBase;
import com.svelteup.app.backend.aop.aspects.owninguserpk.SurrogateOwningUserPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * SurrogateOwningUserNonPkAccesCheckAspect performs object level permissions for JPA models which are
 * SurrogateOwningUserNonPK type entites.
 */
@Component()
@Aspect()
public class SurrogateOwningUserNonPkAccesCheckAspect extends AopBase {

    /**
     * Executes after afterReturningOwningUserNonPrimaryKeyPermissionCheck  returns an instance of OwningUserNonPrimaryKeySurrogateEntity.
     * @param joinPoin The method JoinPoint containing the method arguments of  afterReturningOwningUserNonPrimaryKeyPermissionCheck
     * @param owningUserNonPrimaryKeySurrogateEntity the returned OwningUserNonPrimaryKeySurrogateEntity
     * @return returns the param owningUserNonPrimaryKeySurrogateEntity.
     * @throws Http403Exception Throws Http403Exception when the user is not the owning user of the entity.
     */
    @AfterReturning(
            returning = "owningUserNonPrimaryKeySurrogateEntity",
            pointcut="execution(*  *.afterReturningOwningUserNonPrimaryKeyPermissionCheck(..))"
    )
    public OwningUserNonPrimaryKeySurrogateEntity owningUserNonPrimaryKeyPermissionCheck(JoinPoint joinPoin, OwningUserNonPrimaryKeySurrogateEntity owningUserNonPrimaryKeySurrogateEntity) throws Http403Exception {
        this.nullObjectCheck(owningUserNonPrimaryKeySurrogateEntity,"null OwningUserPrimaryKeySurrogateEntity passed to method.", SurrogateOwningUserPkAccessChecker.class);
        this.objectArgsCheck(joinPoin,2,"owningUserNonPrimaryKeyPermissionCheck",this.getClass().toString());
        String authenticatedUsername = (String) joinPoin.getArgs()[0];
        Object objectUUID = owningUserNonPrimaryKeySurrogateEntity.getSurrogateId();

        if(!authenticatedUsername.equals(owningUserNonPrimaryKeySurrogateEntity.getOwningUsername()))
            this.throwHttp403("accessCheck",joinPoin.getClass().getName(),authenticatedUsername,objectUUID);

        return owningUserNonPrimaryKeySurrogateEntity;
    }


    /**
     * Performs access check on OwningUserNonPrimaryKeySurrogateEntity before entity access.
     * @param joinPoin the JoinPoint which contains the neccessary
     * @return OwningUserNonPrimaryKeySurrogateEntity passed into the beforeOwningUserNonPrimaryKeyPermissionCheck.
     * @throws Http403Exception Throws 403 exception when user cannot access the entity.
     */
    @Before("execution(*  *.beforeOwningUserNonPrimaryKeyPermissionCheck(..))")
    public OwningUserNonPrimaryKeySurrogateEntity beforeOwningUserNonPrimaryKeySurrogateEntity(JoinPoint joinPoin) throws Http403Exception {
        OwningUserNonPrimaryKeySurrogateEntity owningUserNonPrimaryKeySurrogateEntity =(OwningUserNonPrimaryKeySurrogateEntity) joinPoin.getArgs()[1];
        this.nullObjectCheck(owningUserNonPrimaryKeySurrogateEntity,"null OwningUserNonPrimaryKeySurrogateEntity passed to method.", SurrogateOwningUserPkAccessChecker.class);
        this.objectArgsCheck(joinPoin,2,"beforeOwningUserNonPrimaryKeySurrogateEntity",this.getClass().toString());
        this.owningUserNonPrimaryKeyPermissionCheck(joinPoin,owningUserNonPrimaryKeySurrogateEntity);
        return owningUserNonPrimaryKeySurrogateEntity;
    }
}
