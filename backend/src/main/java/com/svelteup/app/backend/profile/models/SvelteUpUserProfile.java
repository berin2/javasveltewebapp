package com.svelteup.app.backend.profile.models;

import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.profile.dtos.AddressDto;
import com.svelteup.app.backend.profile.dtos.PhoneNumberDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Map;
import java.util.TreeMap;

@Entity
@Data()
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor()
public class SvelteUpUserProfile extends OwningUserPrimaryKeySurrogateEntity {
    protected Integer phoneNumberAreaCode;
    protected Integer phoneNumberPhoneNumber;

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String addressLineOne;
    protected String userProfileCity;
    protected String userProfileState;
    protected String userProfileCountry;
    protected Integer userProfileZipCode;

    private String aboutMe;
    private String imagePath;



    public SvelteUpUserProfile(SvelteUpUser userToInitFor)
    {
        super(userToInitFor.getUsername());
        this.productReviewScoreCard = null;
    }

    @JoinColumn(insertable = true,updatable = false,nullable = false, referencedColumnName = "owningUsername")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @BatchSize(size = 1)
    protected ProductReviewScoreCard productReviewScoreCard;

    public void updatePhoneNumber(PhoneNumberDto update_dto)
    {
        this.phoneNumberAreaCode  = update_dto.phoneNumberAreaCode;
        this.phoneNumberPhoneNumber =  update_dto.phoneNumberPhoneNumber;
    }

    public PhoneNumberDto toPutPhoneNumberDto()
    {
        PhoneNumberDto returnDto = new PhoneNumberDto(this);
        return returnDto;
    }

    public AddressDto toAddressDto()
    {
        AddressDto addressDto = new AddressDto(this);
        return addressDto;
    }

    public void updateAddress(AddressDto update_dto)
    {
        this.addressLineOne = update_dto.addressLineOne;
        this.userProfileState =  update_dto.userProfileState;
        this.userProfileCountry = update_dto.userProfileCountry;
        this.userProfileZipCode = update_dto.zipCode;
    }

    public SvelteUpUserProfileDto toUserProfileDto(String image) {
        SvelteUpUserProfileDto returnDto = new SvelteUpUserProfileDto(this,image);
        return  returnDto;
    }

    public void updateUserProfileDto(SvelteUpUserProfileDto update_dto) {
        this.aboutMe = update_dto.aboutMe;
    }

    public String returnPhoneNumberString()
    {
        return  phoneNumberAreaCode.toString() + phoneNumberPhoneNumber.toString();
    }

    public void initializeSvelteUpUserProfile(SvelteUpUserProfileDto svelteUpUserProfileDto)
    {
        this.firstName  =  svelteUpUserProfileDto.firstName;
        this.lastName  =  svelteUpUserProfileDto.lastName;

        this.addressLineOne  = svelteUpUserProfileDto.addressLineOne;
        this.userProfileState = svelteUpUserProfileDto.userProfileState;
        this.userProfileCity =  svelteUpUserProfileDto.userProfileCity;
        this.userProfileZipCode = svelteUpUserProfileDto.zipCode;
    }
}
