<script lang="ts">

    import {ProductDto} from "../../Dto/store/ProductDto";
    import {HttpRequest} from "../../Services/HttpClients/HttpClient";
    import ApiService from "../../Services/ApiService/ApiService";
    import {onMount} from "svelte";
    import ProductCard from "./ProductCard.svelte";
    import ProductAddCard from "./ProductAddCard.svelte";

    let testDescirptyion: string = "hiuhihih hiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihihhiuhihih";
    export let productDto: ProductDto = null;
    let storeProducts: ProductDto [] = [productDto, productDto, productDto];

    function fetchStoreProducts() {
        //request:HttpRequest = new HttpRequest(ApiServic)
    }

    onMount(
        () => {
            storeProducts = storeProducts.fill(productDto, 0, 10);
        }
    )


</script>

<style>
    .store-grid {
        height: 80vh;
    }
</style>
<section class="container h-100 app-container align-content-between store-grid bg-white border shadow">
    <section class="row justify-content-center ">
        <div class="col-12 d-flex  flex-row justify-content-between border-bottom">
            <h1 class="app-color-green fw-bold">Store</h1>
            <h1 class="bi bi-shop text-secondary"></h1>
        </div>
    </section>
    <section class="row">
        <div class="col-12 border-bottom pb-3">
            <h3 class="text-secondary">
                Tips and Tricks
            </h3>
            <p>
                You can add up to twenty products to your store. Six images of each product are allowed.
                Images can say a thousand words so make sure your product tell a good story.
                <ProductAddCard />
            </p>
        </div>
        <section class="container-fluid">
            <section class="row">
                {#each storeProducts as storeProduct }
                    <ProductCard product={storeProduct} />
                {/each}
            </section>
        </section>
    </section>
</section>