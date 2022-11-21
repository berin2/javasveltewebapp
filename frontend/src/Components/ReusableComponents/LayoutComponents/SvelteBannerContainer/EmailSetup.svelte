<script  lang="ts">
    import TextInput from "../../FormControls/TextInput.svelte";
    import BaseButton from "../../Buttons/BaseButton.svelte";
    import ThemeClass from "../../ThemeClasses/ThemeClass";
    export let bannerHeader: string = "Email";
    import {httpClient} from "../../../../Stores/HttpClient";
    import {HttpClient, HttpRequest, PUT} from "../../../../Services/HttpClients/HttpClient";
    import ApiService from "../../../../Services/ApiService/ApiService";
    import SvelteUpUserProfileDto from "../../../../Dto/profile/SvelteUpUserProfileDto";
    import authenticationStore from "../../../../Stores/AuthenticationStore";
    let client: HttpClient  = $httpClient;

    let resendVerificationSuccess: boolean|null = null;
    let updateEmailSuccess:boolean|null = null;

    let email: string = "";
    let confirmEmail: string = "";

    $: matches = email  == confirmEmail;
    $: buttonDisabled =  email == "" || email != confirmEmail;

    function resendVerificationEmail()
    {
        let req: HttpRequest = new HttpRequest(PUT,ApiService.getEmailTokenUrl(),true,{"content-type":"application/json"},{});
        client.httpRequest(req,() => resendVerificationSuccess = true, ()  => resendVerificationSuccess = false);
    }


    function updateEmail() {
        if (matches)
        {
            let dto: SvelteUpUserProfileDto = new SvelteUpUserProfileDto();
            dto.buildEmailDto(email);
            let req: HttpRequest = new HttpRequest(PUT,ApiService.getEmailUrl(),true,{"content-type":"application/json"},dto);
            client.httpRequest(req,() => {email="";confirmEmail="";updateEmailSuccess=true;},()=>{updateEmailSuccess=false;})
        }
    }
</script>

<style>
    .app-banner-img {
        height: 120px;
        width: 120px;
    }

    .header-border  {border-width: 2px !important;}

    .left-border{border-left: 2px solid black;}

    .panel-right
    {
        background-color: #e0f0ff;
    }

    @media (min-width: 576px) {}
    @media(min-width: 768px) {}
    @media(min-width: 992px){}
    @media(min-width: 1200px){}
</style>

<section class="container h-100 app-container align-content-between ">
    <section class="row justify-content-center">
        <div class="col me-auto border-bottom border-warning header-border">
        </div>
        <div class="col d-flex flex-row justify-content-center align-items-center flex-grow-1">
            <img src="/images/SvelteUpBrand.png" class="app-banner-img rounded-circle border shadow-lg header-border"/>
        </div>
        <div class="col ms-auto border-bottom border-success header-border"></div>
    </section>
    <section class="row justify-content-center">
        <h1 class="col-12 text-center display-4 text-warning">{bannerHeader}</h1>
    </section>
    <section class="row d-flex flex-grow-1 mt-4 bg-white shadow">
        <div class="col-md-6 col-12 d-flex flex-column justify-content-between py-4">
            <h1 class="">Email</h1>
            <p>You don't have an email verified and linked to your account.</p>
            <p>Your account email is test-email@email.com</p>
            <p>You need to have an email linked to your account to have full access to our site.</p>
            <div>
                <h3>Didn't Recieve Verification Email?</h3>
                <p>
                    You should recieve an email shortly after registiring. Check your spam folder if you haven't gottent
                    an email after a few minutes. Always verify that any emails you recieve are from @svelteup.app.
                </p>
                <div class="my-1">
                {#if resendVerificationSuccess != null}
                    {#if resendVerificationSuccess == true}
                        <span>Verification  email resent.</span>
                    {:else }
                        <span class="text-warning">Verification email resent failed.</span>
                    {/if}

                {/if}
                </div>

                <section>
                    <input type="button" class="btn btn-success" value="Resend Email" on:click={() => resendVerificationEmail()}/>
                </section>
            </div>
        </div>
        <div class="col-md-6 col-12 d-flex flex-column justify-content-between panel-right py-4">
            <h1 class="text-black">Update Email</h1>
            <p class=" ">If you'd like to link a different email to the account, you can do it here.</p>
                <TextInput col="col-6 " iconValue="bi bi-person-badge " labelValue="Email" placeholderValue="Email" spanValue="" bind:textValue={email} />
                <TextInput col="col-6 " iconValue="bi bi-person-badge " labelValue="Confirm Email" placeholderValue="Confirm Email" spanValue="" bind:textValue={confirmEmail} />
                <p>You'll need to check your email and follow the verification link. Always ensure that any links you click are sent from our team, and that you'd requested the email. END</p>
                <p></p>
            <div>
                <p class="text-warning" >
                    {#if matches && buttonDisabled}
                       <span class="text-success">You can update your email here.</span>
                    {:else if !matches && buttonDisabled}
                        <span class="text-warning">Your emails need to match.</span>
                    {:else}
                            <span></span>
                    {/if}
                </p>
                <BaseButton color="  btn-outline-warning " value="Update Email" onClick={updateEmail} disabled={buttonDisabled}/>
            </div>
        </div>
    </section>
</section>