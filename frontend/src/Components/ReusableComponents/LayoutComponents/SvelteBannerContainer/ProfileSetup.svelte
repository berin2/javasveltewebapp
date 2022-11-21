<script lang="ts">
    import SvelteUpUserProfileDto from "../../../../Dto/profile/SvelteUpUserProfileDto";
    import BaseButton from "../../Buttons/BaseButton.svelte";
    import TextInput from "../../FormControls/TextInput.svelte";
    import NumberInput from "../../FormControls/NumberInput.svelte";
    import TextArea from "../../FormControls/TextArea.svelte";
    import FileUpload from "../../FormControls/FileUpload.svelte";
    import ThemeClass from "../../ThemeClasses/ThemeClass";
    import {httpClient} from "../../../../Stores/HttpClient";
    import {HttpClient, HttpRequest, POST} from "../../../../Services/HttpClients/HttpClient";
    import {isFalsy} from "../../../../Validators/IsFalsyObjectValidator";
    import CheckInput from "../../FormControls/CheckInput.svelte";
    import authenticationStore from "../../../../Stores/AuthenticationStore";
    import ApplicationUser from "../../../../Dto/auth/ApplicationUser";
    import ApiService from "../../../../Services/ApiService/ApiService";
    import UserProfileDto from "../../../../Dto/profile/UserProfileDto";

    let user:ApplicationUser = $authenticationStore;
    let client: HttpClient = $httpClient;

    let profileDto: SvelteUpUserProfileDto = new SvelteUpUserProfileDto();
    let placeholder: any = "";

    let firstName: string = "";
    let lastName: string = "";
    let aboutMe: string = "";
    let profileImage:  any|null =null;

    let addressLineOne: string = "";
    let zipCode: number = 0;

    let addPhoneNumber: boolean = false;
    let areaCode: number = 0;
    let phoneNumber: number = 0;
    let isValidProfile: boolean = false;

    let errMsg:string = "";


    $: {
        isValidProfile = !isFalsy(firstName) && !isFalsy(lastName);
        isValidProfile =  isValidProfile && !isFalsy(zipCode);
        isValidProfile = isValidProfile && (addPhoneNumber ? !isFalsy(areaCode) && !isFalsy(phoneNumber) : true);
    }


    const FIRST_LAST_INVALID: string = "First and Last name can't be empty.";
    const PHONE_NUMBER: string = "Phone numbers must have a three digit area code followed by a seven digit phone number";
    const ZIP_INVALID: string ="You must supply a five digit zipcode.";


    function buildDto(): SvelteUpUserProfileDto {
        let identityDto:SvelteUpUserProfileDto = new SvelteUpUserProfileDto();

        identityDto.firstName = firstName;
        identityDto.lastName = lastName;
        identityDto.zipCode = zipCode;
        identityDto.addressLineOne = addressLineOne;

        if(addPhoneNumber)
        {
            areaCode = areaCode;
            phoneNumber = phoneNumber;
        }
        return identityDto;
    }

    function success(resp: ApplicationUser) :void
    {
        user.isIdentityVerified = resp.isIdentityVerified;
        $authenticationStore = {...user};
        alert(JSON.stringify({...user}))

    }

    function fail(errorReason:any) :void
    {
        errMsg = "We couldn't validate your profile. Please try again in a little while.";
    }

    function dispatchRequest(): void
    {
        alert("on clicker");
        let request:HttpRequest = new HttpRequest(POST,ApiService.getVerifyIdentityUrl(),true,{"content-type":"application/json"},buildDto());
        client.httpRequest(request,success, fail);
    }


</script>

<style>
    .profile-image
    {
        height: 80px;
        width: 80px;
    }

    .maps-tile
    {
        height: 170px;
        width: 170px;
    }
</style>

<section class="container h-100 app-container align-content-between bg-white border ">

    <section class="row justify-content-center bg-white m-0 p-0 w-100 " >
        <h1 class=" bad col-6 text-start display-4 text-secondary">Profile Setup</h1>
        <div class="col-6 d-flex flex-row  align-items-center justify-content-end align-items-end">
            <h1 class="bi bi-person-circle"></h1>
        </div>
    </section>
    <section class="row d-flex flex-grow-1 mt-0 bg-white shadow">
        <div class="col-md-6 col-12 d-flex flex-column justify-content-between py-4">
            <h1 class="">Profile</h1>
            <p>
                Before you can buy or sell on our platform, you'll need to setup your profile. It's  quick and easy.
                Always remember, we don't share sensitive private information with anyone.
            </p>
            <p>
                Enter your first and last name for billing and payment purposes.
                We show your first name as part of your user profile to users of our platform.
                Your last name is confidential.
            </p>
            <TextInput labelValue="First Name" spanValue="Required" placeholderValue="..." bind:textValue={firstName} />
            <TextInput labelValue="Last Name" spanValue="Required" placeholderValue="..." bind:textValue={lastName}/>
        </div>
        <div class="col-md-6 col-12 d-flex flex-column justify-content-between panel-right py-4 app-color-light-blue">
            <h1 class="text-black">Location</h1>
            <p>Your address and location determines who can buy and sell from you. Fill in  your zip code and we'll do the rest. </p>
            <section class="d-flex flex-row">
                <section class="flex-grow-1">
                    <NumberInput stepValue="1" labelValue="Zipcode" spanValue="Required" bind:numberValue={zipCode} placeholderValue="..." />
                    <TextInput labelValue="Address Line" bind:textValue={addressLineOne} placeholderValue="..." spanValue="Optional"/>
                </section>
            </section>

        </div>
        <div class="col-md-6 col-12 d-flex flex-column justify-content-between panel-right py-4">
            <h1 class="text-black">About You</h1>
            <p>Give us a one to three sentence introduction, and upload a profile picture. First impressions are important so make yours good.</p>
            <TextArea labelValue="Intro" textValue={aboutMe}/>
            <section class="d-flex flex-row">
                <section>
                    <FileUpload labelValue="Profile Image" spanValue="Optional" bind:imageValue={profileImage}/>
                    <span class="text-muted">Images are compressed down to 1MB,if needed. Png file format recommended.</span>
                </section>
                <section class="d-flex flex-column justify-content-center align-items-center m-4" >
                    {#if profileImage != null}
                        <img class="profile-image rounded-circle bg-warning" src={profileImage}/>
                    {:else }
                        <div class="profile-image rounded-circle bg-success app-color-white d-flex flex-row justify-content-center align-items-center">
                            <h1 class="display-3">T</h1>
                        </div>
                    {/if}
                </section>
            </section>
        </div>
        <div class="col-md-6 col-12 d-flex flex-column justify-content-between panel-right py-4 app-color-light-blue">
            <h1 class="text-black">Phone Number</h1>
            <p>You can add a phone number, if you'd like to add an extra layer of security to your account. This is highly recommended but not required.</p>
            <div class="container-fluid">
                <div class="row">
                    <NumberInput bind:numberValue={areaCode} col=" col-3 " disabled={!addPhoneNumber} labelValue="Area code" placeholderValue="NNN" spanValue="Three digit area code."/>
                    <NumberInput bind:numberValue={phoneNumber} col=" col-5 " disabled={!addPhoneNumber} labelValue="Phone Number" placeholderValue="NNNNNNN" spanValue="Seven digit phone number."/>
                </div>
            </div>
            <CheckInput  labelValue="Link a phone."  bind:checkValue={addPhoneNumber}  spanValue="Add phone Number?"/>
        </div>
        <div class="col-12 py-3 border-top">
            <div>
            </div>
            <BaseButton  value="Submit Profile" color={ThemeClass.app_control_green} disabled={!isValidProfile} onClick={dispatchRequest}/>
            <span  class="mx-1 text-warning">{errMsg}</span>
        </div>
    </section>
</section>