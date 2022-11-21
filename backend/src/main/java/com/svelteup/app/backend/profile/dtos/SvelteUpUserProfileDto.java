package com.svelteup.app.backend.profile.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svelteup.app.backend.productorder.dto.ProductReviewScoreCardDto.PutProductReviewScoreCardDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SvelteUpUserProfileDto {
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

   public PutProductReviewScoreCardDto productReviewScoreCard;

   List<String> foeList;

   public SvelteUpUserProfileDto(SvelteUpUserProfile profileEntity,String awsImage)
   {
      this.phoneNumberAreaCode = profileEntity.getPhoneNumberAreaCode();
      this.phoneNumberPhoneNumber = profileEntity.getPhoneNumberPhoneNumber();
      this.firstName = profileEntity.getFirstName();
      this.lastName = profileEntity.getLastName();
      this.email = profileEntity.getEmail();
      this.addressLineOne = profileEntity.getAddressLineOne();
      this.userProfileCity = profileEntity.getUserProfileCity();
      this.userProfileState = profileEntity.getUserProfileState();
      this.userProfileCountry = profileEntity.getUserProfileCountry();
      this.zipCode = profileEntity.getUserProfileZipCode();
      this.aboutMe =  profileEntity.getAboutMe();
      this.image = awsImage;
      this.productReviewScoreCard = profileEntity.getProductReviewScoreCard() != null ? profileEntity.getProductReviewScoreCard().toExistingDto():null;
      foeList = new ArrayList<>();
   }
}