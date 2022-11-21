package com.svelteup.app.backend.aop.aspects.paireduser;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;

import javax.transaction.NotSupportedException;

public interface OwningUserPairedNonPkEntityAccessCheckAOPTarget {
    /**
     * Checks if the user is the owningUser or the  secondaryOwningUser.
     * @param authenticatedUser The user to check against the entity.
     * @return  Returns an instance of entity if and only if access checks pass.
     * @throws Http403Exception Thrown when the user is not listed as the owning or secondary user.
     * @throws NotSupportedException Thrown when the method is not supported.
     */
    PairedUserNonPrimaryKeyEntity afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws Http403Exception, NotSupportedException;
    /**
     * Checks if the user is the owningUser or the  secondaryOwningUser.
     * @param authenticatedUser The user to check against the entity.
     * @return  Returns an instance of entity if and only if access checks pass.
     * @throws Http403Exception Thrown when the user is not listed as the owning or secondary user.
     * @throws NotSupportedException Thrown when the method is not supported.
     */
    PairedUserNonPrimaryKeyEntity afterReturningSecondaryOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws Http403Exception, NotSupportedException;


    /**
     * afterReturningIsOwningUserCheck is used to identify if the user is the owning or secondary owning user.
     * @param authenticatedUser The user to check against the entity.
     * @return  a true Boolean indicating that the authenticatedUser is the owningUser, or false if the user
     * is the secondary user. Before calling this method, it's important to ensure the user is either the owningUser
     * or the secondaryOwningUser.
     */
    PairedUserNonPrimaryKeyEntity afterReturningIsOwningUserOrSecondaryUserCheck(String authenticatedUser, PairedUserNonPrimaryKeyEntity entity) throws NotSupportedException;


}

