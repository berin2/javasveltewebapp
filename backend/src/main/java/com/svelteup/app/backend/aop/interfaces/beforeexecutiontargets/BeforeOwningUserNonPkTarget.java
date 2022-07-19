package com.svelteup.app.backend.aop.interfaces.beforeexecutiontargets;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;

/**
 * BeforeOwningUserNonPkTarget is used as an Pointcut designator to perform AOP object permission checking
 * before a service attempts to access an OwningUserNonPrimaryKeySurrogateEntity  in anyway.
 */
public interface BeforeOwningUserNonPkTarget<OwningUserEntity> {
    public OwningUserNonPrimaryKeySurrogateEntity beforeAccessCheck(String authenticatedUser,OwningUserEntity owningEntity) throws Http403Exception;
}
