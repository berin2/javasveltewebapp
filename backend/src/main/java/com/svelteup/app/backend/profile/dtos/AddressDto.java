package com.svelteup.app.backend.profile.dtos;

import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Dto class used to transmit data.")
public class AddressDto {
    public String addressLineOne;
    public String userProfileCity;
    public String userProfileState;
    public String userProfileCountry;
    public Integer zipCode;

    public AddressDto(SvelteUpUserProfile entity)
    {
        this.addressLineOne = entity.getAddressLineOne();
        this.userProfileCity = entity.getUserProfileCity();
        this.userProfileState = entity.getUserProfileState();
        this.userProfileCountry = entity.getUserProfileCountry();
        this.zipCode = entity.getUserProfileZipCode();
    }
}
