<script lang="ts">

    import {ProductDto} from "../../../Dto/store/ProductDto";
    import {HttpRequest} from "../../../Services/HttpClients/HttpClient";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {onMount} from "svelte";
    import ProductCard from "../ProductCard.svelte";
    import ProductAddCard from "../ProductAddCard.svelte";
    import LoadingStore from "./LoadingStore.svelte";
    import EmptyStore from "./EmptyStore.svelte";

    export let productDto: ProductDto = null;
    let storeProducts: ProductDto [] = [productDto, productDto, productDto];

    let productStoreLoaded: boolean = false;

    function fetchStoreProducts() {
        //request:HttpRequest = new HttpRequest(ApiServic)
    }

    onMount(
        () => {
            storeProducts = storeProducts.fill(productDto, 0, 10);
        }
    );

    function onMountSuccess() {
    }

    function onMountFail() {
    }


</script>

<style>
    .store-grid {
        min-height: 80vh !important;
        height: auto !important;
    }
</style>
<section class="container h-100 app-container align-content-between  bg-white border shadow  overflow-scroll">
    <section class="row justify-content-center sticky">
        <div class="col-12 d-flex  flex-row justify-content-between ">
            <h1 class="app-color-green fw-bold">Store</h1>
            <h1 class="bi bi-shop text-secondary"></h1>
        </div>
    </section>
    <section class="row sticky-top bg-white">
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
    <section class="row justify-content-center align-items-center d-flex flex-fill ">
        {#if !productStoreLoaded}
                <EmptyStore/>
        {:else }
                {#each storeProducts as storeProduct }
                    <section class="col-3">
                        <ProductCard product={storeProduct} />
                    </section>
                {/each}
        {/if}
    </section>
</section>