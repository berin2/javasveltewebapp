package com.svelteup.app.backend.userlifecycle.services.initializers.registrationinitializers;

import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.AuthorityRepository;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.stereotype.Component;

@Component
public class SvelteUpUserInitializer extends EntityInitializer<UserRegisterDto> {


    protected final SSvelteUpUser svelteUpUserService;
    protected final AuthorityRepository authorityRepository;

    public SvelteUpUserInitializer(SSvelteUpUser svelteUpUserService,AuthorityRepository authorityRepository1)
    {
        super(false);
        this.svelteUpUserService = svelteUpUserService;
        this.authorityRepository = authorityRepository1;
    }

    @Override
    protected void initialize(UserRegisterDto userRegisterDto) {
        SvelteUpUser initializedUser = svelteUpUserService.initializeUser(userRegisterDto);
    }
}
