package com.svelteup.app.backend.aop.aspects.owninguserpk;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;

import javax.transaction.NotSupportedException;
import java.util.UUID;


/**
 * OwningUserPrimaryKeyPermissionAOPTarget is implemented by Services which manage subclasses of
 * OwningUserNonPkEntity and which need to perform object level permissions for those entites.
 * The two below methods are targeted by pointcut and perform permission checking based on
 * usernames.
 * @param <Entity> The subclass of OwningUserPrimaryKeySurrogateEntity which to perform permission checking for.
 */
public interface OwningUserPrimaryKeyPermissionAOPTarget<Entity extends OwningUserPrimaryKeySurrogateEntity> {
    Entity afterReturningOwningUserPrimaryKeyPermissionCheck(String authenticatedUser, Entity surrogateId) throws Http403Exception, NotSupportedException;
    Entity beforeOwningUserPrimaryKeyPermissionCheck(String authenticatedUser, Entity entity) throws Http403Exception, NotSupportedException;
}
