package com.svelteup.app.backend.profile.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SvelteUpUserAccountDto {
   public Integer phoneNumberAreaCode;
   public Integer phoneNumberPhoneNumber;

   public String firstName;
   public String lastName;
   public String email;

   public String addressLineOne;
   public String userProfileCity;
   public String userProfileState;
   public String userProfileCountry;
   public Integer zipCode;

   public String aboutMe;
   public String image;

   List<String> foeList = new ArrayList<>();
}
