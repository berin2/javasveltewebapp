<script lang="ts">
    import AppBar from "./Components/AppBar/AppBar.svelte";
    import TextInput from "./Components/ReusableComponents/FormControls/TextInput.svelte";
    import AppModal from "./Components/ReusableComponents/LayoutComponents/AppModal.svelte";
    import LoginComponent from "./Components/Login/LoginComponent.svelte";
    import {onDestroy, onMount} from "svelte";
    import {HttpClient, HttpRequest} from "./Services/HttpClients/HttpClient";
    import NavDrawer from "./Components/AppBar/NavDrawer/NavDrawer.svelte";
    import authenticationStore from "./Stores/AuthenticationStore"

    export let name: string;
    import {Router, Route} from 'svelte-navigator';
    import NotFoundRoutes from "./Routes/NotFoundRoutes.svelte";
    import LoggedInRoutes from "./Routes/LoggedInRoutes.svelte";
    import LoggedOutRoutes from "./Routes/LoggedOutRoutes.svelte";
    import ApiService from "./Services/ApiService/ApiService";
    import ApplicationUser from "./Dto/auth/ApplicationUser";

    import {ProductDto} from "./Dto/store/ProductDto";
    import TestRouteds from "./Routes/TestRouteds.svelte";
    import heartBeatStore from "./Components/HeartBeat/store/HeartBeatStore";
    import StoreService from "./Stores/StoreService";
    import {httpClient} from "./Stores/HttpClient";


    let arr: string [] = ["hi", "hello", "hey"];
    let client: HttpClient = $httpClient;
    let page_size: number = 2;

    let user: ApplicationUser = $authenticationStore;
    let productDto: ProductDto = null;

    function onMountAuthSuccess(navDto: ApplicationUser): void {

        let authUser: ApplicationUser = new ApplicationUser();
        authUser.updateSelf(navDto);
        authenticationStore.set(authUser);
        $authenticationStore = {...$authenticationStore};


        //@ts-ignore
        $heartBeatStore.updateSelf(authUser);
        $heartBeatStore = $heartBeatStore;

    }

    function onMountAuthFail(reason: any): void {
        authenticationStore.set(new ApplicationUser());
        $authenticationStore = $authenticationStore;
    }



    onMount(() => {
        StoreService.resetStore();
        let req: HttpRequest = new HttpRequest("GET", ApiService.getAuthOnMountUrl(), true);
        client.dispatchRequest(req, onMountAuthSuccess, onMountAuthFail,(arg:object) => arg);
    })

    onDestroy(() => StoreService.resetStore());

</script>

<style>
	.nav-drawer
	{
		height: 100vh;
		left: 0px !important;
		position: absolute !important;
		top:0px !important;
		width: 25vw;
	}

	main {
		text-align: center;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;
	}
	h1 {
		color: #ff3e00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
	}
	.app-bar-margin
	{
		height: 80px;
		width: 100%;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}
</style>

<Router>
    <AppBar/>
    <NotFoundRoutes/>
    <section class="app-container">
        <TestRouteds/>
        {#if $authenticationStore.authenticated === true}
            <LoggedInRoutes/>
        {:else}
            <LoggedOutRoutes/>
        {/if}
    </section>


</Router>


