<script>
    import {Route} from 'svelte-navigator';
    import EmailSetup from "../Components/ReusableComponents/LayoutComponents/SvelteBannerContainer/EmailSetup.svelte";
    import ProfileSetup
        from "../Components/ReusableComponents/LayoutComponents/SvelteBannerContainer/ProfileSetup.svelte";
    import authenticationStore from "../Stores/AuthenticationStore";
    import StoreDisplay from "../Components/Store/StoreDisplay.svelte";
    import PaymentGrid from "../Components/Payment/PaymentOptions.svelte";
    import PaymentUpdate from "../Components/Payment/PaymentUpdate.svelte";
    import PaymentView from "../Components/Payment/PaymentView.svelte";
    import AccountSettings from "../Components/Account/AccountSettings.svelte";


    $: showProfileSetupRoute = $authenticationStore && $authenticationStore.isIdentityVerified != true;
    $: showEmailSetupRoute = $authenticationStore && authenticationStore.isEmailValidated != true;
    $: accountSetupComplete = $authenticationStore && $authenticationStore.isIdentityVerified == true && $authenticationStore.isEmailValidated == true;
</script>

{#if showEmailSetupRoute}
    <Route path="verifyemail"><EmailSetup/></Route>
{/if}
{#if showProfileSetupRoute}
    <Route path="profilesetup"><ProfileSetup/></Route>
{/if}

{#if accountSetupComplete}
    <Route path="store"><StoreDisplay/></Route>
    <Route path="payment"><PaymentView/></Route>
    <Route path="account"><AccountSettings/></Route>
{/if}



