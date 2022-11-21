import type {ProductReviewScoreCardDto} from "./ProductReviewScoreCardDto";

class SvelteUpUserProfileDto
{
    public  phoneNumberAreaCode:number|null;
    public  phoneNumberPhoneNumber:number|null

    public  firstName:string|null;
    public  lastName:string|null;
    public  email:string|null;

    public addressLineOne:string|null;
    public userProfileCity:string|null;
    public userProfileState:string|null;
    public userProfileCountry:string|null;
    public zipCode:number|null;

    public aboutMe:string|null;
    public image:string|null;
    public productReviewScoreCard: ProductReviewScoreCardDto;

    public constructor() {}

    public buildEmailDto(email:string)
    {
        this.email = email;
    }

    public buildProfileDto(image:any, aboutMe:string)
    {
        this.image = image;
        this.aboutMe = aboutMe;
    }

    public buildIdentityDto(firstName:string, lastName:string, areaCode:number,phoneNumberNumber:number, addressLine:string, userProfileState:string, userProfileCountry:string)
    {
        this.firstName  = firstName;
        this.lastName = lastName;
        this.phoneNumberAreaCode = areaCode;
        this.phoneNumberPhoneNumber = phoneNumberNumber;
        this.addressLineOne  = addressLine;
        this.userProfileState  = userProfileState;
        this.userProfileCountry  = userProfileCountry;
    }


}

export default SvelteUpUserProfileDto;