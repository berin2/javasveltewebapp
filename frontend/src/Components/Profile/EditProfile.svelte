<script lang="ts">
    import TextInput from "../ReusableComponents/FormControls/TextInput.svelte";
    import {Link} from "svelte-navigator";
    import SvelteUpUserProfileDto from "../../Dto/profile/SvelteUpUserProfileDto";
    import NumberInput from "../ReusableComponents/FormControls/NumberInput.svelte";
    import TextArea from "../ReusableComponents/FormControls/TextArea.svelte";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import Graph from "../../Datastructures/Graph";
    import ImageGrid from "../ReusableComponents/LayoutComponents/ImageComponentReusables/ImageGrid.svelte";
    import FileUpload from "../ReusableComponents/FormControls/FileUpload.svelte";

    const INPUTCOLCLASS: string = "col-md-8 col-10";

    enum UpdateProfileEnum {
        NULL_PROFILE,
        NON_NULLPROFILE
    }

    let graph: Graph<UpdateProfileEnum> = new Graph(2);

    let profile: SvelteUpUserProfileDto | null = {
        aboutMe: "Some sample text",
        addressLineOne: "Some sample text",
        email: "Some sample text",
        firstName: "Some sample text",
        image: "Some sample text",
        lastName: "Some sample text",
        phoneNumberAreaCode: 234,
        phoneNumberPhoneNumber: 456341,
        productReviewScoreCard: null,
        userProfileCity: "Some sample text",
        userProfileCountry: "Some sample text",
        userProfileState: "Some sample text",
        zipCode: 1223,
        buildEmailDto(email: string): void {
        },
        buildIdentityDto(firstName: string, lastName: string, areaCode: number, phoneNumberNumber: number, addressLine: string, userProfileState: string, userProfileCountry: string): void {
        },
        buildProfileDto(image: any, aboutMe: string): void {
        }
    };
</script>
<style></style>

<section class="container bg-white border">
    <section class="row ">
        <section class=" col-12 d-flex flex-row justify-content-between ">
            <div class="form-group d-flex flex-row align-items-center justify-content-end my-1">
                <button class=" btn btn-outline-success bi bi-arrow-left-circle" on:click|preventDefault={()=>alert("No back!")}> Back To Profile</button>
            </div>
        </section>
        <section class="col-md-6 col-12 p-0 m-0">
            <section class="container-fluid">
                <section class="row ">
                    <section class="col-12 container-fluid">
                        <section class="row  justify-content-center">
                            <div class="col-12 justify-content-between">
                                <h3>Profile Update</h3>
                                <h3 class="bi bi-pencil-square text-success"></h3>
                            </div>
                            <div class="col-12 text-center border-bottom mb-3">
                                Password,Email or email need updating? Check out the account
                                <Link to="/account">
                        <span>
                            <h5 class="text-primary">settings fix layout later</h5>
                        </span>
                                </Link>
                            </div>
                            <TextInput col={INPUTCOLCLASS} labelValue="First Name" textValue={profile.firstName}/>
                            <TextInput col={INPUTCOLCLASS} labelValue="Last Name" textValue={profile.lastName}/>
                            <TextInput col={INPUTCOLCLASS} labelValue="Address" textValue={profile.addressLineOne}/>
                            <TextInput col={INPUTCOLCLASS} labelValue="City" textValue={profile.userProfileCity}/>
                            <TextInput col={INPUTCOLCLASS} labelValue="State" textValue={profile.userProfileState}/>
                            <NumberInput col={INPUTCOLCLASS} labelValue="Zip" textValue={profile.zipCode} min="0" max="99999" stepValue="1"/>
                            <TextArea col={INPUTCOLCLASS} labelValue="About Me" textValue={profile.aboutMe} />
                            <div class="col-md-6 col-12 justify-content-end my-2">
                                <BaseButton  onClick={()=>alert("Not implemented")} value="Update" color={ThemeClass.app_control_gray} />
                            </div>

                        </section>
                    </section>
                </section>
            </section>
        </section>
        <section class="col-md-6 col-12 bg-secondary p-0 m-0 bg-white">
            <section class="container-fluid m-0">
                <div class="row">
                    <ImageGrid col="6" headerText="Image Preview" >
                        <div slot="slot-one">
                            <FileUpload labelValue="Img Selector" spanValue="Max image size 1.5 MB" iconValue="bi bi-toggle-on"/>
                        </div>
                        <div slot="slot-two">
                            <BaseButton value="Upload"></BaseButton>
                        </div>
                    </ImageGrid>
                </div>
            </section>
        </section>
        </section>
</section>
