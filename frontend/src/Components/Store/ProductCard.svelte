<script lang="ts">
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import ImageService from "../../Services/Utils/ImageService";
    import {ProductDto} from "../../Dto/store/ProductDto";
    import {range} from "../../Services/Utils/ArrayUtils";
    import {fade} from "svelte/transition"
    import StringUtils from "../../Services/Utils/StringUtils";
    import {DELETE, HttpClient, HttpRequest} from "../../Services/HttpClients/HttpClient";
    import ApiService from "../../Services/ApiService/ApiService";
    import UUIDdto from "../../Dto/UUIDdto";
    import {httpClient} from "../../Stores/HttpClient";
    import {onMount} from "svelte"
    import {throwError} from "svelte-preprocess/dist/modules/errors";

    export let product: ProductDto = null;
    export let containingElementHeight = 200;
    export let removeFunction: (product:ProductDto) => void | null = null;
    let client:HttpClient = $httpClient;

    $: productDisplayImage = !isFalsy(product) && !isFalsy(product.productImageStrings.length) ?
        ImageService.getJpegFrom64ByteArray(product.productImageStrings[0]):
        null;
    $: renderImage = productDisplayImage != null && productDisplayImage.length;

    $: {
        //if dto isnt falsy, image array insnt falsy, and first image isnt falsy
        if (!isFalsy(product) && !isFalsy(product.productImageStrings) && !isFalsy(product.productImageStrings.length))
            productDisplayImage = product.productImageStrings[0];
    }

    function deleteSuccess(){
        removeFunction(product);
    }
    function deleteFail(){}
    function deleteProduct(){
        let deleteDto:UUIDdto = new UUIDdto(product.surrogateId);
        let request:HttpRequest = new HttpRequest(DELETE,ApiService.getProductUrl(),true,ApiService.getDefaultHeaders(),deleteDto);
        client.dispatchRequest(request,deleteSuccess,deleteFail)

    }

    onMount(() => {
        if(isFalsy(removeFunction))
        {
            throwError("falsy function supplied to removeFunction of component. This is a critical failure. A function must be supplied.")
        }
    })
</script>
<style>
    .color-styling
    {
        background-color: #f0f0f0;
        border:1px solid #d6d6d6;
        height:auto;
    }

    .inner-content-styling
    {
        background-color: #d6d6d6;
        height: 40px;
        width: 40px;
    }

    .product-thumbnail{
        width:  75px !important;
        height: auto !important;
    }

    @media(min-width: 768px) {

        .product-thumbnail{
            width: 45px !important;
            height: auto !important;
        }
        
    }
</style>

{#each range(1) as element}

    <div class="color-styling container-fluid my-2" transition:fade={{duration:700}} style={`width: ${containingElementHeight*2}px; height: ${containingElementHeight}px`}>
        <div class="row w-100 h-100 justify-content-between ">
            <div class="col-3 d-flex flex-row justify-content-center align-items-center ">
                    <img class="inner-content-styling product-thumbnail rounded-2" src={productDisplayImage} alt="No image found"/>
            </div>
            <div class="col-9 d-flex flex-column   align-items-start bg-white  px-2 text-truncate">
                    <div class="text-secondary d-flex flex-row justify-content-between w-100 border-bottom">
                       <h6 class="me-auto">{product.productName}</h6>
                        <i class="bi bi-cup-straw text-secondary"></i>
                    </div>
                    <p class="text-secondary" >{StringUtils.truncate(product.productDescription,28)}</p>
                    <p >Cost: {product.productCost}$</p>
                    <p>Estimated Calories: {product.productCost}</p>
                    <div class="w-100 d-flex flex-row justify-content-between">
                        <section class="hover rounded text-center flex-grow-1">
                            <i class="bi bi-eye"></i>
                            <span>view/update</span>
                        </section>
                        <section class="hover rounded text-center flex-grow-1" on:click={deleteProduct}>
                            <i class="bi bi-x-lg w-100 hover"></i>
                            <span>delete</span>
                        </section>
                    </div>
            </div>
        </div>
    </div>
{/each}