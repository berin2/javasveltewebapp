<script lang="ts">
    import ViewProps from "../../ViewProps/ViewProps";
    import ViewType from "../../../Dto/ViewType";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {HttpClient, HttpRequest, POST} from "../../../Services/HttpClients/HttpClient";
    import {processProductDto, ProductDto} from "../../../Dto/store/ProductDto";
    import TemplateProductUpdateAddView from "./TemplateProductUpdateAddView.svelte";
    import {httpClient} from "../../../Stores/HttpClient";

    export let productDto: ProductDto = null ;
    let client:HttpClient = $httpClient;

    let props: ViewProps = new ViewProps(
        ViewType.CREATE_VIEW,
        dispatchFunction,
        () => {productDto =  new ProductDto(ProductDto.emptyDto());},
        () => {},
        ()  => {},
        false,
        "Product creation wasn't succesful. Try again later."
    );

    function dispatchFunction(): void {
        alert("hi!");
        let request: HttpRequest = new HttpRequest(POST, "/test-three", true, ApiService.getDefaultHeaders(),productDto.toApiDto());
        client.dispatchRequest(request,() => props.errorFlag = false,  () => props.errorFlag = true,processProductDto );
    }
</script>

<TemplateProductUpdateAddView propObject={props} bind:productDto={productDto}/>