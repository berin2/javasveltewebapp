<script lang="ts">
    import PasswordInput from "../ReusableComponents/FormControls/PasswordInput.svelte";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import TextInput from "../ReusableComponents/FormControls/TextInput.svelte";

    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import {httpClient, apiServiceStore} from "../../Stores/HttpClient";
    import authenticationStore from "../../Stores/AuthenticationStore";
    import {onMount} from "svelte";
    import {HttpRequest, POST} from "../../Services/HttpClients/HttpClient";
    import {LoginDto} from "../../Dto/LoginDto";

    import {fade} from "svelte/transition";
    import ApiService from "../../Services/ApiService/ApiService";
    import ApplicationUser from "../../Dto/auth/ApplicationUser";

    let username: string = "";
    let password: string = "";
    let loginAttemptFailed: boolean = false;

    let success: (response: object) => void = (response: object) => {
        debugger;
        loginAttemptFailed = false;
        let newUser: ApplicationUser = new ApplicationUser();
        newUser.updateSelf(response);
        authenticationStore.set(newUser);
        $authenticationStore = $authenticationStore;
    }
    let fail: (err) => void = () => {
        //$authenticationStore = false;
        loginAttemptFailed = true;
        "hi";
    }

    let attemptLogin: () => void = () => {
        let request: HttpRequest = new HttpRequest(POST, ApiService.getLoginUrl(), true, {"content-type": "application/json"}, new LoginDto(username, password))
        $httpClient.dispatchRequest(request, success, fail);
    };


    onMount(
        () => {
            //loginClient = $backEndClientStore;
        }
    )

</script>

<style>
    .login-form
    {
        background-color: #f6f6f6;
        height: auto;
    }
    .brand-float
    {
        height: 70px;
        width: 70px;
        float: right;
    }
</style>
<section class="container app-container" transition:fade>
    <section class="row justify-content-center">
        <h1 class="col-12 text-center">Login</h1>
    </section>
    <section class="row my-0 py-0 h-100 justify-content-center align-items-center">
        <section class="p-4  col-lg-4 col-md-5 col-sm-7 col-sm-10 col-10  border shadow login-form d-flex flex-column overflow-hidden justify-content-between align-items-between border rounded">
            <section class="p-4 mx-0  col-12 d-flex flex-column align-items-center">
                <img src="/images/SvelteUpBrand.png" class="brand-float"/>
            </section>
            <p class="text-muted text-start border-bottom py-2 mx-1 text-center">
                    Login into your registered account to <span class="text-black fw-bold">SvelteUp.</span>
                    <br>
                    No account? Register <a class="link-primary" href="/test">here</a>.
                </p>
        <section class="text-muted">
        </section>

        <div class="container">
            <div class="row h-100 justify-content-center">
                <TextInput col="col-10 " iconValue="bi bi-person-badge " labelValue="Username" placeholderValue="Username" spanValue="" bind:textValue={username} />
                <PasswordInput col="col-10 " iconValue="bi bi-key" labelValue="Password" placeholderValue="Username" spanValue="" bind:passwordValue={password} />
            </div>
            <div class="row justify-content-center my-1">

                <BaseButton  col=" col-9" color={ThemeClass.app_control_green} onClick={attemptLogin} value="Login"  />

                <span class="col-12 text-center py-1 text-warning">
                        {loginAttemptFailed ? "Login failed. Are credentials correct?" : ""}
                    </span>
            </div>

            <section class="text-muted text-center" id="abc">
                Forgot password or username, click <a class="link-primary" href="/placeholder">here</a>
            </section>
            </div>


    </section>
    </section>
</section>