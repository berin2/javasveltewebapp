<script lang="ts">
    import {test} from "svelte/types/compiler/config";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import UpdateProductCardControls from "./CardControls/UpdateProductCardControls.svelte";
    import ImageService from "../../Services/Utils/ImageService";
    import {ProductDto} from "../../Dto/store/ProductDto";

    export let product: ProductDto = null;
    $: productDisplayImage = !isFalsy(product) && !isFalsy(product.productImageStrings.length) ?
        ImageService.getJpegFrom64ByteArray(product.productImageStrings[0]):
        null;
    $: renderImage = productDisplayImage != null && productDisplayImage.length;

    $: {
        //if dto isnt falsy, image array insnt falsy, and first image isnt falsy
        if (!isFalsy(product) && !isFalsy(product.productImageStrings) && !isFalsy(product.productImageStrings.length))
            productDisplayImage = product.productImageStrings[0];

    }
</script>
<style>
    .product-card {
        width: 400px;
    }

    .product-image
    {
        height: 310px;
        width: 310px;
    }


    .card-icon
    {
        font-size: 170px;
    }
</style>


{#if !isFalsy(product)}
    <section class="container m-3 product-card border border-success shadow bg-white app-card">
        <section class="row card-row justify-content-center">
            <h3 class="col-12 text-center">{product.productName}</h3>
        </section>
        <section class="row">
                <div class="col-12 d-flex flex-row justify-content-center">
                    {#if renderImage}
                        <div class="product-image rounded overflow-hidden">
                            <img class="border rounded " src={productDisplayImage}/>
                        </div>
                    {:else }
                        <section class="product-image border d-flex flex-column justify-content-center align-items-center">
                            <h5 class="text-muted">No images uploaded.</h5>
                            <i class="bi bi-file-image-fill card-icon text-secondary fa-10x"></i>
                        </section>
                    {/if}
                </div>
        </section>
        <section class="row justify-content-center">
            <div class="col-12 text-center text-break border-bottom app-bg-light-gray">
                <p class="text-break text-secondary"> {product.productDescription}</p>
            </div>
        </section>

        <section class="row justify-content-center">
            <div class="col-6 text-center text-break text-secondary">
                4/5 stars (123 ratings)
            </div>
            <div class="col-6 text-center text-break">
                {product.productCost}
            </div>
        </section>
        <section class="row justify-content-around">
            <UpdateProductCardControls productSurrogateId={product.surrogateId}/>
        </section>
    </section>
{/if}