package com.svelteup.app.backend.modelcontroller.services.abstractions;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.POwningUserNonPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http404Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import java.util.Optional;
import java.util.UUID;


public abstract class SSurrogateEntity<Key,Entity> extends SHttpExceptionThrower {

    protected RSurrogateJpaRepository<Entity,Key> repository;

    protected  POwningUserNonPkAccessChecker pOwningUserNonPkAccessChecker;

    private SSurrogateEntity() throws NotSupportedException {
        throw new NotSupportedException("Default constructor invocation not permitted for SSurrogateEntity classes.");
    }

    public SSurrogateEntity(RSurrogateJpaRepository<Entity,Key> surrogateJpaRepository)
    {
        this.repository = surrogateJpaRepository;
    }

    @Autowired
    public void setpOwningUserNonPkAccessChecker(POwningUserNonPkAccessChecker pOwningUserNonPkAccessChecker)
    {
        this.pOwningUserNonPkAccessChecker = pOwningUserNonPkAccessChecker;
    }

    public Entity findBySurrogateId(UUID surrogate_id) throws Http404Exception, NotSupportedException {
        Optional<Entity> discoveredProductOptional = this.repository.findBySurrogateId(surrogate_id);
        Entity discoveredEntity = null;
        if(!discoveredProductOptional.isPresent())
            throw new Http404Exception("The product with product id " + surrogate_id.toString() + " was not discovered.");
        discoveredEntity = discoveredProductOptional.get();
        return discoveredEntity;
   }



    /**
     * findById returns an Entity by searching the table by the Entity Id
     * @param id The Entity id
     * @return  discovered Entity object matched to the id
     * @throws Http404Exception Throws http404 if not found
     * @throws NotSupportedException not supported if the method is not supported.
     */
    public Entity findById(Key id) throws Http404Exception, NotSupportedException {
        Optional<Entity> discoveredProductOptional = this.repository.findById(id);
        Entity discoveredEntity = null;
        if(!discoveredProductOptional.isPresent())
            throw new Http404Exception("The product with product id " + id.toString() + " was not discovered.");
        discoveredEntity = discoveredProductOptional.get();
        return discoveredEntity;
    }

    /**
     * doesNotExistById ensures a user does not exist by id.
     * @param username the user making the request.Can  be passed the  constant SYSTEM to indicate the system is making the request and not a specific user.
     * @param id The object Id to search the table with.
     * @throws Http500Exception Throws when the id does exist in the table schema.
     */
    public void doesNotExistById(String username,Key id) throws Http500Exception
    {
        if(this.repository.existsById(id))
            this.throwHttp500("doesNotExistById",this.getClass().getName(),username," The application expected an entity not to exist under the key passed to the method, but an entity was discovered.");
    }

    public void save(Entity entity)
    {
        this.repository.save(entity);
    }

}
