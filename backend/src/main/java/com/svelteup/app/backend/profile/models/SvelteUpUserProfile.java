package com.svelteup.app.backend.profile.models;

import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserAccountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

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

    @JoinColumn(insertable = true,updatable = false,nullable = true, referencedColumnName = "owningUsername")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @BatchSize(size = 1)
    @NonNull protected ProductReviewScoreCard productReviewScoreCard;

    public void updatePhoneNumber(SvelteUpUserAccountDto update_dto)
    {
        this.phoneNumberAreaCode  = update_dto.phoneNumberAreaCode;
        this.phoneNumberPhoneNumber =  update_dto.phoneNumberPhoneNumber;
    }

    public SvelteUpUserAccountDto toPutPhoneNumberDto()
    {
        SvelteUpUserAccountDto returnDto = new SvelteUpUserAccountDto();
        returnDto.phoneNumberAreaCode = this.phoneNumberAreaCode;
        returnDto.phoneNumberPhoneNumber = this.phoneNumberPhoneNumber;
        return returnDto;
    }

    public SvelteUpUserAccountDto toAddressDto()
    {
        SvelteUpUserAccountDto returnDto = new SvelteUpUserAccountDto();
        returnDto.addressLineOne = this.addressLineOne;
        returnDto.userProfileState = this.userProfileState;
        returnDto.userProfileCountry = this.userProfileCountry;
        returnDto.zipCode = this.userProfileZipCode;

        return returnDto;
    }

    public void updateAddress(SvelteUpUserAccountDto update_dto)
    {
        this.addressLineOne = update_dto.addressLineOne;
        this.userProfileState =  update_dto.userProfileState;
        this.userProfileCountry = update_dto.userProfileCountry;
        this.userProfileZipCode = update_dto.zipCode;
    }

    public SvelteUpUserAccountDto toUserProfileDto(String image) {
        SvelteUpUserAccountDto returnDto = new SvelteUpUserAccountDto();
        returnDto.aboutMe  = this.aboutMe;
        returnDto.image = image;

        return  returnDto;
    }

    public void updateUserProfileDto(SvelteUpUserAccountDto update_dto) {
        this.aboutMe = update_dto.aboutMe;
    }

    public String returnPhoneNumberString()
    {
        return  phoneNumberAreaCode.toString() + phoneNumberPhoneNumber.toString();
    }

    public void initializeSvelteUpUserProfile(SvelteUpUserAccountDto svelteUpUserAccountDto)
    {
        this.firstName  =  svelteUpUserAccountDto.firstName;
        this.lastName  =  svelteUpUserAccountDto.lastName;

        this.addressLineOne  = svelteUpUserAccountDto.addressLineOne;
        this.userProfileState = svelteUpUserAccountDto.userProfileState;
        this.userProfileCity =  svelteUpUserAccountDto.userProfileCity;
        this.userProfileZipCode = svelteUpUserAccountDto.zipCode;
    }
}
