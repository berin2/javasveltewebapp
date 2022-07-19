package com.svelteup.app.backend.aop.aspects.paireduser;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
@NoArgsConstructor
public class SSurrogateOwningUserSecondaryOwningUserChecker implements OwningUserPairedNonPkEntityAccessCheck<PairedUserNonPrimaryKeyEntity>
{
    /**
     * Checks if the user is the owningUser or the  secondaryOwningUser.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entitySurrogateId The entity surrogate Id passed to the calling proxied method.
     * @return Returns an instance of entity if and only if access checks pass.
     * @throws Http403Exception      Thrown when the user is not listed as the owning or secondary user.
     * @throws NotSupportedException Thrown when the method is not supported.
     */
    @Override
    public PairedUserNonPrimaryKeyEntity afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, UUID entitySurrogateId) throws Http403Exception, NotSupportedException {
        return null;
    }

    /**
     * afterReturningIsOwningUserCheck is used to identify if the user is the owning or secondary owning user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entitySurrogateId The entity  surrogateId
     * @return a true Boolean indicating that the authenticatedUser is the owningUser, or false if the user
     * is the secondary user. Before calling this method, it's important to ensure the user is either the owningUser
     * or the secondaryOwningUser.
     */
    @Override
    public PairedUserNonPrimaryKeyEntity afterReturningIsOwningUserOrSecondaryUserCheck(String authenticatedUser, UUID entitySurrogateId) throws NotSupportedException {
        return null;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyPermissionCheck is used to identify if the user is the owning or secondary owning user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entity            The entity passed to the calling method.
     * @throws Http403Exception      if the user is not listed as owningUser or secondaryOwningUser
     * @throws NotSupportedException if the method call is not supported in implementing service.
     */
    @Override
    public PairedUserNonPrimaryKeyEntity beforeOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws Http403Exception, NotSupportedException {
        return null;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyPermissionCheck is used to identify if the user is the owning user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entity            The entity passed to the calling method.
     * @throws Http403Exception      if the user is not listed as owningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service.
     */
    @Override
    public PairedUserNonPrimaryKeyEntity beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws Http403Exception, NotSupportedException {
        return null;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck is used to identify if the user is the secondary user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entity            The entity passed to the calling method.
     * @throws Http403Exception      if the user is not listed as secondaryUser.
     * @throws NotSupportedException if the method call is not supported in implementing service.
     */
    @Override
    public PairedUserNonPrimaryKeyEntity beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck ensures a user is neither the primary or secondary owning user.
     *
     * @param authenticatedUser the user to permission check.
     * @param entity            the user to check permissions for.
     * @return entity the entity passed as a parameter.
     * @throws Http403Exception      if the user is not listed as secondaryUser and primaryOwningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service
     */
    @Override
    public PairedUserNonPrimaryKeyEntity beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws Http403Exception, NotSupportedException {
        return entity;
    }
}
