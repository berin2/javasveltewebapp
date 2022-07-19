package com.svelteup.app.backend.aop.interfaces.afterreturningtargets;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import org.aspectj.lang.JoinPoint;

public interface OwningUserAopAccessChecker <OwningUserEntity>{
    public void owningEntityAccessCheck(JoinPoint joinPoin, OwningUserEntity owningUserNonPkEntity) throws Http403Exception;
    public void accessCheck(JoinPoint joinPoin, OwningUserEntity owningUserNonPKEntity) throws Http403Exception;
}
