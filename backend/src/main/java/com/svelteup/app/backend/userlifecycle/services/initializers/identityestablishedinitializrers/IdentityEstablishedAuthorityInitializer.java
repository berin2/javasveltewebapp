package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.services.SSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IdentityEstablishedAuthorityInitializer extends EntityInitializer<EstablishCustomerIdentityDto> {
    protected final SSvelteUpUser svelteUpUser;
    public IdentityEstablishedAuthorityInitializer(SSvelteUpUser svelteUpUser) {
        super(false);
        this.svelteUpUser = svelteUpUser;
    }

    public void initialize(EstablishCustomerIdentityDto establishCustomerIdentityDto)
    {
        establishCustomerIdentityDto.user = svelteUpUser.grantIdentityEstablishedAuthority(establishCustomerIdentityDto.user);
    }
}
