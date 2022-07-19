package com.svelteup.app.backend.aop.aspects.paireduser;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;

import javax.transaction.NotSupportedException;
import java.util.UUID;

public interface OwningUserPairedNonPkEntityAccessCheck<Entity  extends PairedUserNonPrimaryKeyEntity> {
    /**
     * Checks if the user is the owningUser or the  secondaryOwningUser.
     * @param authenticatedUser The user to check against the entity.
     * @param entitySurrogateId The entity surrogate Id passed to the calling proxied method.
     * @return  Returns an instance of entity if and only if access checks pass.
     * @throws Http403Exception Thrown when the user is not listed as the owning or secondary user.
     * @throws NotSupportedException Thrown when the method is not supported.
     */
    Entity afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, UUID entitySurrogateId) throws Http403Exception, NotSupportedException;

    /**
     * afterReturningIsOwningUserCheck is used to identify if the user is the owning or secondary owning user.
     * @param authenticatedUser The user to check against the entity.
     * @param entitySurrogateId The entity  surrogateId
     * @return  a true Boolean indicating that the authenticatedUser is the owningUser, or false if the user
     * is the secondary user. Before calling this method, it's important to ensure the user is either the owningUser
     * or the secondaryOwningUser.
     */
    Entity afterReturningIsOwningUserOrSecondaryUserCheck(String authenticatedUser, UUID entitySurrogateId) throws NotSupportedException;

    /**
     * beforeOwningUserPairedNonPrimaryKeyPermissionCheck is used to identify if the user is the owning or secondary owning user.
     * @param authenticatedUser The user to check against the entity.
     * @param entity The entity passed to the calling method.
     * @throws  Http403Exception if the user is not listed as owningUser or secondaryOwningUser
     * @throws NotSupportedException if the method call is not supported in implementing service.
     */
    Entity beforeOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, Entity entity) throws Http403Exception, NotSupportedException;
    /**
     * beforeOwningUserPairedNonPrimaryKeyPermissionCheck is used to identify if the user is the owning user.
     * @param authenticatedUser The user to check against the entity.
     * @param entity The entity passed to the calling method.
     * @throws  Http403Exception if the user is not listed as owningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service.
     */
    Entity beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(String authenticatedUser, Entity entity) throws Http403Exception, NotSupportedException;
    /**
     * beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck is used to identify if the user is the secondary user.
     * @param authenticatedUser The user to check against the entity.
     * @param entity The entity passed to the calling method.
     * @throws  Http403Exception if the user is not listed as secondaryUser.
     * @throws NotSupportedException if the method call is not supported in implementing service.
     */
    Entity beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck(String authenticatedUser, Entity entity) throws Http403Exception, NotSupportedException;

    /**
     * beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck ensures a user is neither the primary or secondary owning user.
     * @param authenticatedUser the user to permission check.
     * @param entity the user to check permissions for.
     * @return entity the entity passed as a parameter.
     * @throws Http403Exception if the user is not listed as secondaryUser and primaryOwningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service
     */
    Entity beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(String authenticatedUser,Entity entity) throws Http403Exception, NotSupportedException;
}

