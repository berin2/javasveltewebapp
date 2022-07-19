package com.svelteup.app.backend.profile.dtos;

import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "SocialProfileDto represents a DTO that displays a users SocialProfile summary which includes an image, firstname, and about me.")
public class SocialProfileDto {
    public byte [] profileImage;
    public String firstName;
    public String aboutMe;

    public SocialProfileDto(SvelteUpUserProfile profile, byte [] image)
    {
        this.firstName = profile.getFirstName();
        this.profileImage = image;
        this.aboutMe = profile.getAboutMe();
    }
}
