package com.svelteup.app.backend.modelcontroller.services.abstractions;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.POwningUserNonPkAccessChecker;
import com.svelteup.app.backend.aop.aspects.owninguserpk.POwningUserPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import java.util.UUID;

/**
 *
 */

public class SSurrogateEntityOwningUserPk<Entity extends OwningUserPrimaryKeySurrogateEntity >
        extends  SSurrogateEntity<String, Entity>
        implements IAccessChecker<String,Entity> //NonPKSurrogateEntity has lookup key of UUID
{

    protected POwningUserPkAccessChecker pOwningUserPkAccessChecker;

    public SSurrogateEntityOwningUserPk(RSurrogateJpaRepository<Entity, String> surrogateJpaRepository) {
        super(surrogateJpaRepository);
    }

    @Autowired
    public void setPowningUserNonPkAccessChecker(POwningUserPkAccessChecker pOwningUserPkAccessChecker)
    {
        this.pOwningUserPkAccessChecker = pOwningUserPkAccessChecker;
    }

    @Override
    public Entity findBySurrogateIdWithCheck(String authenticatedUser,String primaryId) throws NotSupportedException
    {
        Entity owningUserPkEntity = super.findById(primaryId);
        this.pOwningUserPkAccessChecker.afterReturningOwningUserPrimaryKeyPermissionCheck(authenticatedUser,owningUserPkEntity);
        return owningUserPkEntity;
    }

}
