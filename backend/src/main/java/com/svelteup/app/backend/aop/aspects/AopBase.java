package com.svelteup.app.backend.aop.aspects;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;

public class AopBase extends SHttpExceptionThrower {

    protected final String INVALID_JOIN_POIN_ARGS = "Method %s in class %s expected argument length %s, but actual argument length was %s. The associated object identifier is %s.";
    protected final String INVALID_AUTHENTICATED_USERNAME = "Method %s in class %s expected non empty username, but recieved an empty username.";

    public AopBase()
    {
    }

    protected void nullObjectCheck(Object entity, String msg, Class clazz) throws Http500Exception
    {
        if(entity == null)
        {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String authenticatedUser = principal == null ? "NULL_USER_IN_SEC_CONTEXT" : principal.toString();
            this.throwHttp500("nullObjectCheck", clazz.getName(), authenticatedUser);
        }
    }

    protected void objectArgsCheck(JoinPoint joinPoin, Integer expectedLength, String methodName, String className) throws Http500Exception
    {
        Object [] objectArgs = joinPoin.getArgs();
        if(objectArgs == null)
            this.throwHttp500(methodName,className,"SYSTEM","JoinPoint object args are set to null.This is not allowed for managing Aspect.");
        else if(!expectedLength.equals(expectedLength))
            this.throwHttp500(methodName,className,"SYSTEM","JoinPoint object args are not of the expected length for the manging Aspect.");
    }

    protected void objectEventArgsCheck(JoinPoint joinPoin, Integer expectedLength, String methodName, String className) throws Http500Exception
    {
        Object [] objectArgs = joinPoin.getArgs();

        if(objectArgs == null)
            this.throwHttp500(methodName,className,"SYSTEM","JoinPoint object args are set to null.This is not allowed for managing Aspect.");
        else if(!expectedLength.equals(1))
            this.throwHttp500(methodName,className,"SYSTEM","JoinPoint object args are not of the expected length for the manging Aspect.");
    }

}
