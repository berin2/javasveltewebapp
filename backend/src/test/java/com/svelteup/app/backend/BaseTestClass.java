package com.svelteup.app.backend;

import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import org.springframework.beans.factory.InitializingBean;

/*
* Base class for all tests requiring the presence of users.
* This class creates two users, test and test2.
* */
public class BaseTestClass implements InitializingBean {
    protected RSvelteUpUser svelteUpRepository;

    protected SvelteUpUser testUserOne;
    protected SvelteUpUser testUserTwo;

    public BaseTestClass(RSvelteUpUser repository)
    {
        svelteUpRepository = repository;
    }

    private BaseTestClass()
    {}


    @Override
    public void afterPropertiesSet() throws Exception {
        Authority verified = new Authority("verified");
        AccountAuthority testAuthority = new AccountAuthority("test",verified);

    }
}
