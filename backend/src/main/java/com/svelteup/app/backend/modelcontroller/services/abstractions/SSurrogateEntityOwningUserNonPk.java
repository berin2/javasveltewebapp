package com.svelteup.app.backend.modelcontroller.services.abstractions;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.POwningUserNonPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import java.util.UUID;

/**
 *
 * @param <Key> The lookUp Id of the entity repository
 * @param <Entity> The return entity of the reepository.
 */

public class SSurrogateEntityOwningUserNonPk<Key,Entity extends OwningUserNonPrimaryKeySurrogateEntity>
        extends  SSurrogateEntity<Key, Entity>
        implements IAccessChecker<UUID,Entity> //NonPKSurrogateEntity has lookup key of UUID
{

    protected POwningUserNonPkAccessChecker pOwningUserNonPkAccessChecker;

    public SSurrogateEntityOwningUserNonPk(RSurrogateJpaRepository<Entity, Key> surrogateJpaRepository) {
        super(surrogateJpaRepository);
    }

    @Autowired
    public void setPowningUserNonPkAccessChecker(POwningUserNonPkAccessChecker powningUserNonPkAccessChecker)
    {
        this.pOwningUserNonPkAccessChecker = powningUserNonPkAccessChecker;
    }

    @Override
    public Entity findBySurrogateIdWithCheck(String authenticatedUser,UUID surrogateId) throws NotSupportedException
    {
        Entity owningUserPkEntity = super.findBySurrogateId(surrogateId);
        this.pOwningUserNonPkAccessChecker.afterReturningOwningUserNonPrimaryKeyPermissionCheck(authenticatedUser,owningUserPkEntity);
        return owningUserPkEntity;
    }

    public void accessCheck(String authenticatedUser, Entity entity) throws NotSupportedException
    {
        this.pOwningUserNonPkAccessChecker.afterReturningOwningUserNonPrimaryKeyPermissionCheck(authenticatedUser,entity);
    }

}
