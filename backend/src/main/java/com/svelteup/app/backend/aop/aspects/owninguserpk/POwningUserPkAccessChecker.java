package com.svelteup.app.backend.aop.aspects.owninguserpk;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.OwningUserNonPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
public class POwningUserPkAccessChecker implements OwningUserPrimaryKeyPermissionAOPTarget {

    @Override
    public OwningUserPrimaryKeySurrogateEntity afterReturningOwningUserPrimaryKeyPermissionCheck(String authenticatedUser, OwningUserPrimaryKeySurrogateEntity owningUserPrimaryKeySurrogateEntity) throws Http403Exception, NotSupportedException {
        return owningUserPrimaryKeySurrogateEntity;
    }

    @Override
    public OwningUserPrimaryKeySurrogateEntity beforeOwningUserPrimaryKeyPermissionCheck(String authenticatedUser, OwningUserPrimaryKeySurrogateEntity owningUserPrimaryKeySurrogateEntity) throws Http403Exception, NotSupportedException {
        return owningUserPrimaryKeySurrogateEntity;
    }
}
