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
    import PoppPractice from "./Components/ReusableComponents/LayoutComponents/PoppPractice.svelte";
    import AppInitDto from "./Dto/auth/AppInitDto";
    import ObjectCopyService from "./Services/Utils/ObjectCopyService";
    import ProductCard from "./Components/Store/ProductCard.svelte";
    import StoreDisplay from "./Components/Store/StoreDisplay.svelte";
    import {ProductDto} from "./Dto/store/ProductDto";
    import ImageService from "./Services/Utils/ImageService";
    import ImageGrid from "./Components/ReusableComponents/LayoutComponents/ImageGrid/ImageGrid.svelte";
    import TestRouteds from "./Routes/TestRouteds.svelte";
    import heartBeatStore from "./Components/HeartBeat/store/HeartBeatStore";
    import StoreService from "./Stores/StoreService";


    let arr: string [] = ["hi", "hello", "hey"];
    let testClient: HttpClient = null;
    let page_size: number = 2;

    let user: AppInitDto = $authenticationStore;
    let productDto: ProductDto = null;

    function onMountAuthSuccess(navDto: AppInitDto): void {

        let authUser: AppInitDto = new AppInitDto();

        authUser.updateSelf(navDto);

        $authenticationStore = authUser;
        $authenticationStore = $authenticationStore;

        $heartBeatStore.updateSelf(authUser);
        $heartBeatStore = $heartBeatStore;

    }

    function onMountAuthFail(reason: any): void {
        $authenticationStore = new AppInitDto();
    }



    onMount(() => {
        StoreService.resetStore();
        let req: HttpRequest = new HttpRequest("GET", ApiService.getAuthOnMountUrl(), true);
        testClient = new HttpClient("http://localhost:10000");
        testClient.dispatchRequest(req, onMountAuthSuccess, onMountAuthFail,(arg:object) => arg);
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
	.app-container
	{
		height: 100vh;
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
    <TestRouteds/>
    {#if $authenticationStore.authenticated === true}
        <LoggedInRoutes/>
    {:else}
        <LoggedOutRoutes/>
    {/if}

</Router>


