package com.svelteup.app.backend.security.services;

import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http404Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.noauthrequired.dtos.UserRegisterDto;
import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.AccountAuthorityRepository;
import com.svelteup.app.backend.security.repositories.AuthorityRepository;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SSvelteUpUser extends SHttpExceptionThrower   {
    protected final RSvelteUpUser svelteUpRepository;
    protected final PasswordEncoder encoder;
    protected final AccountAuthorityRepository accountAuthorityRepository;
    protected final AuthorityRepository authorityRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public SvelteUpUser initializeUser(UserRegisterDto initUserData) {
        SvelteUpUser userToRegister =  new SvelteUpUser(initUserData.username, encoder.encode(initUserData.password));
        userToRegister = svelteUpRepository.saveAndFlush(userToRegister);
        return userToRegister;
    }

    public void destroyVerifiedUser(String userToDestroy) {

    }

    public SvelteUpUser findByUsername(String username) throws Http404Exception, Http500Exception
    {
        Optional<SvelteUpUser> discoveredOptional = this.svelteUpRepository.findById(username);
        if(!discoveredOptional.isPresent())
            this.throwHttp404("findByUsername",this.getClass().toString(),"SYSTEM",username,"");

        return discoveredOptional.get();
    }

    /**
     * grantVerifiedAuthority Grants the user verified email authority if said user does not have verified authority.
     * If the user posses verified authority, this function does nothing.
     * @param token The EmailVerificationToken retrieved from the db.
     */
    public SvelteUpUser grantEmailEstablishedAuthority(EmailVerificationToken token)
    {
       SvelteUpUser discoveredUser = this.findByUsername(token.getOwningUsername());

            if(discoveredUser.getIsEmailValidated())
                this.throwHttp500("grantVerifiedAuthority",this.getClass().toString(), discoveredUser.getUsername());

            discoveredUser.setIsEmailValidated(true);
            return this.svelteUpRepository.saveAndFlush(discoveredUser);
    }

    public SvelteUpUser grantIdentityEstablishedAuthority(SvelteUpUser  userFromSecurityContext)
    {
        SvelteUpUser authenticatedUser = this.findByUsername(userFromSecurityContext.getUsername());

        if(authenticatedUser.getIsIdentityValidated())
                this.throwHttp500("grantIdentityEstablishedAuthority",this.getClass().toString(), authenticatedUser.getUsername());

        authenticatedUser.setIsIdentityValidated(true);
        return this.svelteUpRepository.saveAndFlush(authenticatedUser);
    }

    public SvelteUpUser grantFullySetupUserAuthority(SvelteUpUser userFrpmSecurityContext)
    {
        SvelteUpUser userFetchedFromDb = this.findByUsername(userFrpmSecurityContext.getUsername());
        List<AccountAuthority> usersFetchedFromDbAuthorities = userFetchedFromDb.getAccountAuthorityList();
        Authority embeddedAuthority = null;
        Authority fullySetupAuthority = null;

        for(int i = 0; i < usersFetchedFromDbAuthorities.size();i++)
        {
            embeddedAuthority  = usersFetchedFromDbAuthorities.get(i).getAccounts_authority();

            if(Authority.NOT_FULLY_SETUP_ACCOUNT.equals(embeddedAuthority.getAuthority()))
                usersFetchedFromDbAuthorities.remove(i);
        }

        fullySetupAuthority = this.authorityRepository.findAuthorityByAuthority(Authority.FULLY_SETUP_ACCOUNT);
        AccountAuthority fullySetupUserAuthority = new AccountAuthority(userFrpmSecurityContext.getUsername(),fullySetupAuthority);
        userFetchedFromDb.setIsFullySetup(true);
        userFetchedFromDb.getAccountAuthorityList().add(fullySetupUserAuthority);
        this.authorityRepository.saveAndFlush(fullySetupAuthority);
        return this.svelteUpRepository.saveAndFlush(userFetchedFromDb);
    }

    protected void ensureUserIsCompletelyValidated(SvelteUpUser userFromDb) throws Http500Exception
    {
        boolean hasNotCompletelySetup = false;

        for(AccountAuthority accountAuthority : userFromDb.getAccountAuthorityList())
        {
            String authorityString = accountAuthority.getAuthority();
            if(authorityString != null)
                if(authorityString.equals(Authority.NOT_FULLY_SETUP_ACCOUNT))
                    hasNotCompletelySetup = true;

        }

        if(!hasNotCompletelySetup)
            this.throwHttp500("ensureUserIsCompletelyValidated",this.getClass().toString(),userFromDb.getUsername(),"Application Attempted to give fully authenticated authority to the user but the user was not fully authenticated.");
    }

    protected boolean accountSetupComplete(SvelteUpUser userFromDb)
    {
        return userFromDb.getIsEmailValidated() && userFromDb.getIsIdentityValidated();
    }
}
