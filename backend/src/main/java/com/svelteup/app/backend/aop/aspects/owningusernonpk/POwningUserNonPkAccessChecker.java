package com.svelteup.app.backend.aop.aspects.owningusernonpk;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
public class POwningUserNonPkAccessChecker implements OwningUserNonPrimaryKeyPermissionAOPTarget {
    @Override
    public OwningUserNonPrimaryKeySurrogateEntity afterReturningOwningUserNonPrimaryKeyPermissionCheck(String authenticatedUser, OwningUserNonPrimaryKeySurrogateEntity owningUserNonPrimaryKeySurrogateEntity) throws Http403Exception, NotSupportedException {
        return owningUserNonPrimaryKeySurrogateEntity;
    }

    @Override
    public OwningUserNonPrimaryKeySurrogateEntity afterReturningSecondaryOwningUserNonPrimaryKeyPermissionCheck(String authenticatedUser, OwningUserNonPrimaryKeySurrogateEntity entity) throws Http403Exception, NotSupportedException {
        return entity;
    }


    @Override
    public OwningUserNonPrimaryKeySurrogateEntity beforeOwningUserNonPrimaryKeyPermissionCheck(String authenticatedUser, OwningUserNonPrimaryKeySurrogateEntity entity) throws Http403Exception, NotSupportedException {
        return entity;
    }
}
