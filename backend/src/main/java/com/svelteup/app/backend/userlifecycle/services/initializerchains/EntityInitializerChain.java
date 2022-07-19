package com.svelteup.app.backend.userlifecycle.services.initializerchains;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public abstract class  EntityInitializerChain<DtoMethodArg> extends SHttpExceptionThrower {
    private  List<EntityInitializer< DtoMethodArg>>  initializers;

    public EntityInitializerChain()
    {
        this.initializers = new ArrayList<>();
    }
    public EntityInitializerChain(Integer initialCapacity)
    {
        this.initializers = new ArrayList<>(initialCapacity);

    }

    void addToInitializeChain(Integer index,EntityInitializer <DtoMethodArg>  initializer) throws Http500Exception
    {
        if( this.initializers.size() > index && this.initializers.get(index) != null)
            this.throwHttp500("addToInitializeChain",this.getClass().toString(),"SYSTEM"," EntityInitializerIndex has already been assigned. Initializer index cannot be assigned to more than one EntityInitializer.");

        this.initializers.add(index,initializer);
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
    public void doChain(String user, DtoMethodArg objectValue)
    {
        for(EntityInitializer initializer:  this.initializers)
            initializer.execute(user,objectValue);
    }
}
