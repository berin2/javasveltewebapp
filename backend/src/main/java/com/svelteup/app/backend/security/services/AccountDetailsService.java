package com.svelteup.app.backend.security.services;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* AccountDetailsService is used to pull UserDetails from the database for
* SpringSecurity.
* */
@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final RSvelteUpUser RSvelteUpUser;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SvelteUpUser> discoveredAccountDetailsOptional = this.RSvelteUpUser.findById(username);
        SvelteUpUser discoveredDetails = null;

        if(!discoveredAccountDetailsOptional.isPresent())
            throw new UsernameNotFoundException("The requested user was not discovered");

        discoveredDetails = discoveredAccountDetailsOptional.get();
        return discoveredDetails;
    }
}
