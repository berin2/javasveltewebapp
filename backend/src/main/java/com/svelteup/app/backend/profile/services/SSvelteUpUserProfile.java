package com.svelteup.app.backend.profile.services;

import com.svelteup.app.backend.aop.aspects.owninguserpk.OwningUserPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserPk;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.repositories.RSvelteUpUserProfile;
import com.svelteup.app.backend.utils.services.interfaces.IUserLifeCycle;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service()
public class SSvelteUpUserProfile extends SSurrogateEntityOwningUserPk<SvelteUpUserProfile>
        implements IUserLifeCycle<SvelteUpUserProfile,SvelteUpUser>
{

    RSvelteUpUserProfile rSvelteUpUserProfile;

    public SSvelteUpUserProfile(RSvelteUpUserProfile surrogateJpaRepository) {
        super(surrogateJpaRepository);
        this.rSvelteUpUserProfile = surrogateJpaRepository;
    }


    @Override
    public SvelteUpUserProfile initializeUser(SvelteUpUser user) {
        SvelteUpUserProfile profileToCreate =  new SvelteUpUserProfile(user);
        return this.rSvelteUpUserProfile.saveAndFlush(profileToCreate);
    }



    @Override
    public void destroyVerifiedUser(String userToDestroy) {

    }


    public boolean existsByEmail(String email)
    {
        return this.rSvelteUpUserProfile.existsByEmail(email);
    }

}
