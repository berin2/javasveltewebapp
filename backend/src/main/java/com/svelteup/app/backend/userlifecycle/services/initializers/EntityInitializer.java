package com.svelteup.app.backend.userlifecycle.services.initializers;


import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.transaction.NotSupportedException;
import java.io.IOException;

@Data()
@EqualsAndHashCode(callSuper = true)
public abstract class EntityInitializer<DtoMethodArg> extends SHttpExceptionThrower {
    protected boolean isStringInitializer;

    public EntityInitializer(boolean isStringInitializer)
    {
        this.isStringInitializer=isStringInitializer;
    }


    private EntityInitializer(){};

    protected  void initialize(String username)
    {
        this.throwHttp500("initialize",this.getClass().toString(),"SYSTEM","Init by username is not supported by this initializer.");
    }
    protected void initialize(DtoMethodArg methodArg) throws NotSupportedException, ServletException, IOException {
        this.throwHttp500("initialize",this.getClass().toString(),"SYSTEM","Init by DtoMethodArg is not supported by this initializer.");
    }

    @SneakyThrows
    public void execute(String username, DtoMethodArg arg)  {
        if(isStringInitializer)
        {
            if(username == null )
                this.throwHttp500("execute",this.getClass().toString(),"SYSTEM","EntityModel Initializer user String is null.");
            this.initialize(username);
        }
        else
        {
            if(arg == null)
                this.throwHttp500("execute",this.getClass().toString(),"SYSTEM","EntityModel Initializer user DtoObject is null.");
            this.initialize(arg);
        }
    }
}
