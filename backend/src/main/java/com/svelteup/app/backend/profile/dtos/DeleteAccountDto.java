package com.svelteup.app.backend.profile.dtos;

import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;

@ApiModel("DeleteAccountDto is used to carry delete request information from the user.")
public class DeleteAccountDto
{
    public Boolean deleteAccount;
    public Boolean deactivateAccount;

    public DeleteAccountDto()
    {
        this.deleteAccount = false;
        this.deactivateAccount = false;
    }
}
