package com.svelteup.app.backend.security.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity(name="authority")
@Table(name="authority")
@Data @NoArgsConstructor
public class Authority implements GrantedAuthority {
   @Id
    private  String authority;

    public Authority(String authority)
    {
        super();
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.toString();
    }

    @Override
    public String toString(){return this.authority;}

    public static final String NOT_FULLY_SETUP_ACCOUNT = "account_not_fully_setup";
    public static final String FULLY_SETUP_ACCOUNT = "account_fully_setup";
}
