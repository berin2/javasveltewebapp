package com.svelteup.app.backend.security.dtos;

import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@ApiModel(description = "Represents the JSON user will present to login.")
public class UsernamePasswordAuthenticationDto {
     public String username;
     public String password;
}
