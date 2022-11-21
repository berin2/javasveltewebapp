<script type="ts">
    import authenticationStore from "../../Stores/AuthenticationStore";
    import AppBarContentsLoggedOut from "./LoggedOut/AppBarContentsLoggedOut.svelte";
    import AppBarContentsLoggedIn from "./LoggedIn/AppBarContentsLoggedIn.svelte";
    import MessagePanel from "../Messages/components/ChatWindow.svelte";
    import StoreService from "../../Stores/StoreService";
    let authentication = $authenticationStore;
</script>

<style>
    .custom-navbar
    {
        height: 75px;
        background: rgb(245,237,236);
        background: linear-gradient(90deg, rgba(245,237,236,1) 0%, rgba(242,242,242,1) 100%);
    }
</style>

<nav class="border-bottom shadow-sm fixed-top custom-navbar container-fluid mx-0" id="application-navigation-bar" on:LOGOUT_ACTION={() => StoreService.resetStore()}>
    <div class="row justify-content-between align-items-center h-100">
          {#if $authenticationStore.authenticated === true}
           <AppBarContentsLoggedIn/>
            <MessagePanel/>
           {:else }
           <AppBarContentsLoggedOut/>
          {/if}
    </div>

</nav>


