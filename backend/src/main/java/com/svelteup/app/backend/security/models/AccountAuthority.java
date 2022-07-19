package com.svelteup.app.backend.security.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="account_authority")
@Table(name="account_authority")
@Data @NoArgsConstructor
public class AccountAuthority implements GrantedAuthority {
    @Id @GeneratedValue
    private UUID    authority_id;
    private String username;

    @JoinColumn(name="authority_description",referencedColumnName = "authority")
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private Authority accounts_authority;

    public AccountAuthority(String username,Authority accounts_authority)
    {
        super();
        this.username = username;
        this.accounts_authority = accounts_authority;
    }

    @Override
    public String getAuthority() {
        return accounts_authority.getAuthority();
    }

    @Override
    public String toString(){return getAuthority();}
}
