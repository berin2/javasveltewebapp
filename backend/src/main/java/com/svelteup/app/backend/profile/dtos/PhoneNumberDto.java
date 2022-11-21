package com.svelteup.app.backend.profile.dtos;

import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "represents dto used to transmit phone number information from the user profile.")
public class PhoneNumberDto {
      public Integer phoneNumberAreaCode;
      public Integer phoneNumberPhoneNumber;

      public PhoneNumberDto(SvelteUpUserProfile entity){
          this.phoneNumberAreaCode = entity.getPhoneNumberAreaCode();
          this.phoneNumberPhoneNumber = entity.getPhoneNumberPhoneNumber();
      }
}
