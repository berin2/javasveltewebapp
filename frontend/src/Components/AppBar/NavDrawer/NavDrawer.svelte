<script lang="ts">
    import NavDrawerItem from "./NavDrawerItem.svelte";
    import {Link} from "svelte-navigator";
    import {DELETE, HttpClient, HttpRequest} from "../../../Services/HttpClients/HttpClient";
    import {httpClient} from "../../../Stores/HttpClient";
    import ApiService from "../../../Services/ApiService/ApiService";
    import AppInitDto from "../../../Dto/auth/AppInitDto";
    import authenticationStore from "../../../Stores/AuthenticationStore";
    import AccountSetup from "./AccountSetupLinks.svelte";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";
    import StoreService from "../../../Stores/StoreService";

    export let onClick: () => void = () => {
    };
    export let drawerTucked: boolean = true;
    $: drawerAnimationClass = drawerTucked ?
        "nav-drawer nav-drawer-slid-in nav-drawer-slid-in-keyframe d-flex flex-column justify-content-between " :
        "nav-drawer nav-drawer-slid-out nav-drawer-slid-out-keyframe d-flex flex-column justify-content-between ";
    $: modalClass = drawerTucked ?
        " app-modal-dismissed app-modal-dismissed-keyframes " :
        " app-modal app-modal-keyframes ";


    //@ts-ignore
    let userProfile: AppInitDto = $authenticationStore;
    let client: HttpClient = $httpClient;
    let reset: () => void = () => {
        $authenticationStore = new AppInitDto();
    }
    let attemptLogout: () => void = () => {
        let request: HttpRequest = new HttpRequest(DELETE, ApiService.getLogoutUrl(), true, {"content-type": "application/json"});
        client.httpRequest(request, reset, reset);
        StoreService.resetStore();
    }

    const toggleModal: () => void = () => drawerTucked = !drawerTucked;
    const NAV_MENU_LINKS: Object [] = [
        {to: "/", text: "Profile", iconClass: "bi bi-person-circle"},
        {to: "/store", text: "My Store", iconClass: "bi bi-shop"},
        {to: "/", text: "Sales", iconClass: "bi bi-tags"},
        {to: "/", text: "Purchases", iconClass: "bi bi-upc"},
        {to: "/payment", text: "Payment", iconClass: "bi bi-credit-card"},
        {to: "/account", text: "Account", iconClass: "bi bi-gear"},
        {to: "/", text: "Messages", iconClass: "bi bi-chat-square-dots"},
        {to: "/", text: "Contacts", iconClass: "bi bi-person-lines-fill"},
        {to: "/", text: "Favorites", iconClass: "bi bi-bookmark-heart"},
    ]

    const profileSetup: object = {to: "/profilesetup", iconClass: "bi bi-person-circle"};
    const resendEmailVerification: object = {to: "#", iconClass: "bi bi-person-circle"};

    const TOP_MENU_LINKS: Object [] = [
        {to: "/", text: "Fb", iconClass: "bi bi-facebook"},
        {to: "/", text: "Insta", iconClass: "bi bi-instagram"},
    ]
</script>

    <div class={drawerAnimationClass + " shadow-lg"} on:click|preventDefault={onClick()}>
        <section class="d-flex flex-column justi`fy-content-center mx-4">
            <section class="d-flex flex-row align-items-center justify-content-around border-bottom py-2">
                <h3>Brand Text</h3>
            </section>

            <section class="d-flex flex-row  justify-content-around align-items-center pt-5">
                {#each TOP_MENU_LINKS as socialLink}
                    <div class="app-color-gray social-icon d-flex flex-column ">
                        <i class={socialLink.iconClass + " nav-social-icon"} ></i>
                        <p class="text-center mb-0">{socialLink.text}</p>
                    </div>

                {/each}
            </section>
        </section>

        <section class="d-flex flex-column justify-content-center mx-4">
           <div class="text-black border-bottom d-flex justify-content-between align-items-center">
               <h3 class=" text-black">Navigation</h3>
               <span class="bi bi-segmented-nav text-black"></span>
           </div>
            {#each NAV_MENU_LINKS as link}
                <NavDrawerItem {...link}/>
            {/each}
        </section>

        <AccountSetup />

        <div class="nav-drawer-header bg-white d-flex flex-row align-items-center justify-content-around border my-1 px-4">
            <div class="d-flex flex-column">
                <h6 class="fw-bold text-black">John Smith</h6>
                <span class="app-color-gray">john@email.com</span>
            </div>

            {#if !isFalsy(userProfile.profile) && !isFalsy(userProfile.profile.image)}
                <img  class="img-fluid nav-drawer-header-image rounded-circle shadow m-1" src={userProfile.profile.image} alt="img"/>
            {:else}
                <div class="d-flex justify-content-center align-items-center nav-drawer-header-image rounded-circle shadow m-1 bg-success text-white">
                    {userProfile.username.charAt(0).toUpperCase()}
                </div>
            {/if}
        </div>
        <section class="d-flex justify-content-end align-items-center pe-2">
            <input type="button" value="Sign Out" class="btn btn-success" on:click={() => attemptLogout()} />
        </section>
    </div>
<div class={modalClass} on:click|preventDefault={onClick} ></div>





<style>

    @keyframes app-modal-keyframes {
        from{display: none}
        to{display: block}
    }

    @keyframes app-modal-dismissed-keyframes {
        from{display: block}
        to {display: none}
    }
    .nav-drawer
    {
        background-color: #f6f6f6;
        box-sizing: border-box;
        height: 100%;
        position: fixed !important;
        top:0px;
        width: 320px;
        z-index: 10000;
    }

    .nav-drawer-slid-in
    {
        animation-name: nav-drawer-slide-in-keyframe !important;
        left: -525px !important;
    }


    @keyframes nav-drawer-slide-in-keyframe
    {
        from {left: 0px;}
        to{left: -525px;}
    }

    .nav-drawer-slid-out
    {
        animation-name: nav-drawer-slide-out-keyframe;
        animation-duration: 0.25s;
        left: 0px;
    }

    @keyframes nav-drawer-slide-out-keyframe
    {
        from {left: -525px;}
        to{left: 0px;}
    }

    .nav-drawer-header
    {
        height: 100px;
    }

    .nav-drawer-header-image
    {
        height: 50px;
        width: 50px;
    }

    .nav-brand {
        height: 80px;
        width: 80px;
    }

    .nav-social-icon
    {
        font-size: 2rem !important;
    }

    .nav-social-icon:hover
    {
        color: black;
    }
</style>