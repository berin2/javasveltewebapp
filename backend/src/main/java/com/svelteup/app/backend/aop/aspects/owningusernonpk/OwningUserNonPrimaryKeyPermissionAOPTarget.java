package com.svelteup.app.backend.aop.aspects.owningusernonpk;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;

import javax.transaction.NotSupportedException;
import java.util.UUID;


/**
 * OwningUserNonPrimaryKeyPermissionAOPTarget is implemented by Services which manage subclasses of
 * OwningUserNonPkEntity and which need to perform object level permissions for those entites.
 * The two below methods are targeted by pointcut and perform permission checking based on
 * usernames.
 */
public interface OwningUserNonPrimaryKeyPermissionAOPTarget {
    OwningUserNonPrimaryKeySurrogateEntity afterReturningOwningUserNonPrimaryKeyPermissionCheck(String authenticatedUser,OwningUserNonPrimaryKeySurrogateEntity entity) throws Http403Exception, NotSupportedException;
    OwningUserNonPrimaryKeySurrogateEntity afterReturningSecondaryOwningUserNonPrimaryKeyPermissionCheck(String authenticatedUser,OwningUserNonPrimaryKeySurrogateEntity entity) throws Http403Exception, NotSupportedException;
    OwningUserNonPrimaryKeySurrogateEntity beforeOwningUserNonPrimaryKeyPermissionCheck(String authenticatedUser, OwningUserNonPrimaryKeySurrogateEntity entity) throws Http403Exception, NotSupportedException;
}
