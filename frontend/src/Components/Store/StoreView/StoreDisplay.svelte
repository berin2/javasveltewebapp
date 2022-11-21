<script lang="ts">
    import {ProductDto} from "../../../Dto/store/ProductDto";
    import {onMount} from "svelte";
    import ProductCard from "../ProductCard.svelte";
    import ProductAddCard from "../ProductAddCard.svelte";
    import EmptyStore from "./EmptyStore.svelte";
    import UserProfileCard from "../../UserProfile/UserProfileCard.svelte";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {GET, HttpClient, HttpRequest} from "../../../Services/HttpClients/HttpClient";
    import {httpClient} from "../../../Stores/HttpClient";
    import SvelteUpUserProfileDto from "../../../Dto/profile/SvelteUpUserProfileDto";
    import authenticationStore from "../../../Stores/AuthenticationStore";
    import ApplicationUser from "../../../Dto/auth/ApplicationUser";
    import {isFalsy, isFalsyArray} from "../../../Validators/IsFalsyObjectValidator";
    import Graph from "../../../Datastructures/Graph";
    import LoadingStore from "./LoadingStore.svelte";

    let storeProducts: ProductDto [] = [];
    let user: ApplicationUser = $authenticationStore;
    let productStoreLoaded: boolean = false;
    let client: HttpClient = $httpClient;
    enum ProductStoreEnum {
        NOT_LOADED,
        LOADED_BUT_EMPTY,
        LOADED_BUT_NOT_EMPTY
    }

    let viewGraph: Graph<ProductStoreEnum> = new Graph(2);
    viewGraph.currentVertex = ProductStoreEnum.NOT_LOADED;
    $: graphVertex = viewGraph.currentVertex;


    function removeFromStore(product: ProductDto) {
        if (!isFalsy(product) && !isFalsy(product.surrogateId))
            for (let i: number = 0; i < storeProducts.length; i++) {
                if (!isFalsy(storeProducts[i]) && storeProducts[i].surrogateId === product.surrogateId) {
                    storeProducts.splice(i, 1);
                }
            }
        storeProducts = storeProducts;
        if (storeProducts.length === 0) {
            viewGraph.transition(ProductStoreEnum.LOADED_BUT_EMPTY);
            graphVertex = viewGraph.currentVertex;
        }

    }


    onMount(
        () => {

            viewGraph.createEdge(ProductStoreEnum.NOT_LOADED, ProductStoreEnum.LOADED_BUT_EMPTY);
            viewGraph.createEdge(ProductStoreEnum.NOT_LOADED, ProductStoreEnum.LOADED_BUT_NOT_EMPTY);
            viewGraph.createEdge(ProductStoreEnum.LOADED_BUT_NOT_EMPTY, ProductStoreEnum.LOADED_BUT_EMPTY);
            fetchStoreProducts();
        }
    );

    function onMountSuccess(response: any) {

        if (!isFalsyArray(response)) {
            viewGraph.currentVertex = ProductStoreEnum.LOADED_BUT_NOT_EMPTY;
            for (let product of response) {
                storeProducts = [new ProductDto(product), ...storeProducts];
            }
        } else {
            storeProducts = [];
            viewGraph.currentVertex = ProductStoreEnum.LOADED_BUT_EMPTY;
        }
        graphVertex = viewGraph.currentVertex;

    }

    function onMountFail() {
        storeProducts = [];
    }

    function fetchStoreProducts() {
        let request: HttpRequest = new HttpRequest(GET, ApiService.getProducts(user.username), true, ApiService.getDefaultHeaders());
        client.dispatchRequest(request, onMountSuccess, onMountFail)
    }


</script>

<style>
    .store-grid {
        min-height: 80vh !important;
        height: auto !important;
    }
</style>
<section class="container h-100  align-content-between  bg-white border shadow  overflow-scroll">
    <section class="row justify-content-center sticky ">
        <div class="col-12 d-flex  flex-row justify-content-between ">
            <h1 class="app-color-green fw-bold">Store</h1>
            <h1 class="bi bi-shop text-secondary"></h1>
        </div>
    </section>
    <section class="row sticky-top bg-white border-bottom mb-2">
        <div class="col-12  pb-3">
            <h3 class="text-secondary">
                Tips and Tricks
            </h3>
            <p>
                You can add up to twenty products to your store. Six images of each product are allowed.
                Images can say a thousand words so make sure your product tell a good story.
                <ProductAddCard />
            </p>
        </div>
    </section>
    <section class="row">
        <section class="col-12">
            <UserProfileCard />
        </section>
    </section>
    <section class="row justify-content-center align-items-center d-flex flex-fill ">
        {#if graphVertex === ProductStoreEnum.NOT_LOADED}
            <section class="row justify-content-center align-items-center d-flex flex-fill ">
                <LoadingStore/>
            </section>
        {:else if graphVertex === ProductStoreEnum.LOADED_BUT_NOT_EMPTY}
            <section class="row justify-content-start align-items-center d-flex flex-fill ">
            {#each storeProducts as storeProduct }
                <section class="col-md-6 col-12  d-flex flex-row justify-content-center align-items-center">
                    <ProductCard product={storeProduct} removeFunction={removeFromStore} />
                </section>
            {/each}
            </section>
            {:else}
            <section class="row justify-content-center align-items-center d-flex flex-fill ">
                <EmptyStore/>
            </section>
        {/if}
    </section>
</section>