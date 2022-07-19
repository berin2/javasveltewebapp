package com.svelteup.app.backend.noauthrequired.dtos;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "This JSON represents a user registration JSON.")
public class UserRegisterDto {
    public String username;
    public String password;
    public String email;
}
