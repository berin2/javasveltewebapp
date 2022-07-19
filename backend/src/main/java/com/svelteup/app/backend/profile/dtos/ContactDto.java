package com.svelteup.app.backend.profile.dtos;

import io.swagger.annotations.ApiModel;

@ApiModel("ContactDto represents Dto used to transfer contact information from the front end to the backend.")
public class ContactDto {

    public String username;
    public String firstName;
    public String profileImage;

    public ContactDto(String username,String firstName, String profileImage)
    {
        this.username = username;
        this.firstName = firstName;
        this.profileImage = profileImage;
    }
}
