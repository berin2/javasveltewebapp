package com.svelteup.app.backend.security.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="account_details")
@Table(name="account_details")
@Data @NoArgsConstructor
public class SvelteUpUser implements UserDetails, Serializable {
    @Id()
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean account_is_non_expired;
    private Boolean account_is_non_locked;
    private Boolean credentials_is_non_expired;
    private Boolean is_enabled;
    private Boolean isEmailValidated;
    private Boolean isIdentityValidated;
    private Boolean isFullySetup;

    @JoinColumn(name="username",referencedColumnName = "username")
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE,CascadeType.PERSIST}, orphanRemoval = true)
    protected List<AccountAuthority> accountAuthorityList;
    public SvelteUpUser(String username, String password)
    {
        super();
        this.username = username;
        this.password = password;
        this.account_is_non_expired=true;
        this.account_is_non_locked = true;
        this.credentials_is_non_expired=true;
        this.is_enabled=true;
        this.isEmailValidated = false;
        this.isIdentityValidated = false;
        this.accountAuthorityList = new ArrayList<>();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.accountAuthorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.account_is_non_expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.account_is_non_locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentials_is_non_expired;
    }

    @Override
    public boolean isEnabled() {
        return this.is_enabled;
    }

    @Override
    public String toString()
    {
        return this.getUsername();
    }
}
