package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.AccountAuthorityRepository;
import com.svelteup.app.backend.security.repositories.AuthorityRepository;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service()
@Profile("dev")
@DependsOn("SSystemEntitySetupOwningUser")
@NoArgsConstructor
public class SSvelteUpUserSetupOwningUser extends AOwningUserDataSetup {

    @Autowired AuthorityRepository authorityRepository;
    @Autowired AccountAuthorityRepository accountAuthorityRepository;
    @Autowired RSvelteUpUser svelteUpUserRepository;

    Authority fullySetup;
    Authority notFullySetup;

    @PostConstruct
    private void constructUserAuthorities()
    {
        if(authorityRepository.findAuthorityByAuthority(Authority.FULLY_SETUP_ACCOUNT) == null) {
            this.fullySetup  = new Authority(Authority.FULLY_SETUP_ACCOUNT);
            this.fullySetup =  authorityRepository.save(this.fullySetup);
        }

        if(authorityRepository.findAuthorityByAuthority(Authority.NOT_FULLY_SETUP_ACCOUNT) == null) {
            this.notFullySetup  = new Authority(Authority.NOT_FULLY_SETUP_ACCOUNT);
            this.notFullySetup =  authorityRepository.save(this.notFullySetup);
        }
    }

    @Override
    public void setupEntity(OwningUserDataSetupDto dto)
    {

        dto.fullySetupAuthority  = this.fullySetup;
        dto.notFullySetupAuthority =  this.notFullySetup;

        SvelteUpUser user = new SvelteUpUser("user_"+dto.userIndex.toString(),"user_"+dto.userIndex.toString());
        user.setIsIdentityValidated(true);
        user.setIsEmailValidated(true);
        user = svelteUpUserRepository.save(user);
        AccountAuthority accountAuthority = new AccountAuthority(user.getUsername(),fullySetup);
        accountAuthorityRepository.save(accountAuthority);
        user.getAccountAuthorityList().add(accountAuthority);
        user = svelteUpUserRepository.save(user);
        dto.owningUser = user;
    }

}
