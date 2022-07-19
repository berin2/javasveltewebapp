package com.svelteup.app.backend.payment.dtos;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ClientTokenDto is used to send a string representation of the BrainTree client token to the front end.")
public class ClientTokenDto {
    public String username;
    public String clientToken;

    public ClientTokenDto(String authenticatedUser,String clientToken)
    {
        this.username = authenticatedUser;
        this.clientToken = clientToken;
    }
}
