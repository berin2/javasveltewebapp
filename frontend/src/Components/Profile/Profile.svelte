<script lang="ts">
    import authenticationStore from "../../Stores/AuthenticationStore";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import DisplayGroup from "./DisplayGroup.svelte";
    import UserProfileDto from "../../Dto/profile/UserProfileDto";
    import CircularImage from "../ReusableComponents/LayoutComponents/ImageComponentReusables/CircularImage.svelte";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";

    $: profile = $authenticationStore.profile;
    let dto: UserProfileDto;
    const GROUPCLASS: string = "col-12  my-1 text-secondary";
</script>

<section class="container h-100 w-100 shadow">
    <section class="row h-100 ">
        {#if !isFalsy(profile)}
            <section class="col-md-3 col-12 container align-content-between  bg-white border  p-2">
                <div class="row">
                    <h2 class="text-success  border-bottom">
                        Profile
                    </h2>
                    <h5 class="bi bi-grid text text-secondary ms-auto"></h5>
                </div>
                <div class="row">
                    <div class="col-12">
                        {#if profile.image}
                            <CircularImage height={200} src={profile.image} firstLetter={!isFalsy(profile.firstName)? profile.firstName.substring(0,1) : "-"}/>
                        {:else}
                            <p>{profile.firstName}</p>
                        {/if}
                        <h5>
                            {`${profile.firstName} ${profile.lastName}`}
                        </h5>
                    </div>
                    <DisplayGroup colClass={GROUPCLASS} headerText="About Me">
                        <p slot="body">{profile.aboutMe}</p>
                    </DisplayGroup>
                    <DisplayGroup colClass={GROUPCLASS} headerText="Phone Number">
                        <p slot="body">{profile.phoneNumberAreaCode + " " + profile.phoneNumberPhoneNumber}</p>
                    </DisplayGroup>
                    <DisplayGroup colClass={GROUPCLASS} headerText="Address">
                        <section slot="body">
                            <p>
                                {profile.addressLineOne}
                                <br>
                                {`${profile.userProfileCity},${profile.userProfileState}`}
                                <br>
                                {`${profile.zipCode}`}
                                <br>
                            </p>
                        </section>
                    </DisplayGroup>
                    <DisplayGroup colClass={GROUPCLASS} headerText="Email">
                        <p slot="body">
                            {profile.email}
                        </p>
                    </DisplayGroup>
                    <DisplayGroup>
                        <div slot="body">
                            <BaseButton color={ThemeClass.app_control_green} value={"Update Profile"}>
                                <i slot="icon" class="bi bi-pencil-square"/>
                            </BaseButton>
                        </div>
                    </DisplayGroup>
                </div>
            </section>
        {/if}
        <div class="col-md-9 col-12 h-100 " style="background-color: #fafafa;">
            Secondary
        </div>
    </section>
</section>

