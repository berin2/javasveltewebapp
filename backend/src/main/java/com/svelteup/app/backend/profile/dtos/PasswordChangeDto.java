package com.svelteup.app.backend.profile.dtos;

import io.swagger.annotations.ApiModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel("PasswordChangeDto is used to change password change requests from the front end to the back end.")
public class PasswordChangeDto {
    @NotNull @NotBlank
    public String oldPassword;
    @NotNull @NotBlank
    public String newPassword;
}
