package com.svelteup.app.backend.utils.exceptionutils;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@NoArgsConstructor
public abstract class SHttpExceptionThrower {

    public static final String METHOD_NOT_SUPPORTED_ERR = " Method %s is not supported in %s as requested by user %s.";
    public static final String FORBIDDEN_OPERATION_ERR = "Forbidden operation by username %s in method %s of class %s on object with id %s";
    public static final String INTERNAL_SERVER_ERROR = "The Server encountered an error in class %s and method %s requested by user %s.";
    public static final String UNSUPPORTED_OPERATION =  "Unsupported operation called in clasname %s method name %s by username %s";
    public static final String BAD_REQUEST_OPERATION = "Method %s in class %s requested to perform operation for user %s with object identifier %s but the request failed.";
    public static final String RESOURCE_NOT_FOUND = "Method %s in class %s requested to perform operation for user %s with object identifier %s but the object identifier could not  be found.";
    @Value("spring.profiles.active")
    protected String profile;
    public void throwHttp403(String methodName, String className, String authenticatedUser, String appendedMessage) throws Http403Exception
    {
        throw new Http403Exception(String.format(FORBIDDEN_OPERATION_ERR,authenticatedUser,methodName,className,"NOT APPLICABLE", appendedMessage));
    }

    public void throwHttp403(String methodName, String className, String authenticatedUser, Object id) throws Http403Exception
    {
        String idString  = id == null ? "NULL_OBJECT_ID" : id.toString();
        throw new Http403Exception(String.format(FORBIDDEN_OPERATION_ERR,authenticatedUser,methodName,className,idString));
    }
    public void throwHttp405(String methodName, String className, String authenticatedUser) throws Http405Exception
    {
        throw new Http405Exception(String.format(METHOD_NOT_SUPPORTED_ERR,methodName,className,authenticatedUser));
    }

    public void throwHttp500(String methodName, String className, String authenticatedUser) throws Http500Exception
    {
        throw new Http500Exception(String.format(METHOD_NOT_SUPPORTED_ERR,methodName,className,authenticatedUser));
    }

    public void throwHttp500(String methodName, String className, String authenticatedUser, String appendedMessage) throws Http500Exception
    {
        String errorMessageString = String.format(methodName,className,authenticatedUser) + appendedMessage;
        throw new Http500Exception(errorMessageString);
    }

    public void throwUnsupportedOperationException(String className,String methodName,String username)
    {
        if(username == null || username == "")
            username = "USER_NOT_KNOWN";

        throw new UnsupportedOperationException(String.format(this.UNSUPPORTED_OPERATION,className,methodName,username));
    }
    public void throwHttp400(String methodName, String className, String authenticatedUser, Object objectArg) throws  Http400Exception {
        String objectIdentifier = objectArg == null ? "NOT_SUPPLIED" : objectArg.toString();

        throw new Http400Exception(String.format(BAD_REQUEST_OPERATION,methodName,className,authenticatedUser,objectIdentifier));
    }

    public void throwHttp400(String methodName, String className, String authenticatedUser, Object objectArg, String appendedMessage) throws  Http400Exception {
        String objectIdentifier = objectArg == null ? "NOT_SUPPLIED" : objectArg.toString();

        throw new Http400Exception(String.format(BAD_REQUEST_OPERATION,methodName,className,authenticatedUser,objectIdentifier) + " " + appendedMessage);
    }

    public void throwHttp404(String methodName, String className, String authenticatedUser, Object objectArg, String appendedMessage) throws  Http400Exception {
        String objectIdentifier = objectArg == null ? "NOT_SUPPLIED" : objectArg.toString();

        throw new Http404Exception(String.format(RESOURCE_NOT_FOUND,methodName,className,authenticatedUser,objectIdentifier) + " " + appendedMessage);
    }
}
