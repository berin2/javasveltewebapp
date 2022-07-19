<script lang="ts">
    import ViewProps from "../../ViewProps/ViewProps";
    import ViewType from "../../../Dto/ViewType";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {HttpClient, HttpRequest, POST, PUT} from "../../../Services/HttpClients/HttpClient";
    import {processProductDto, ProductDto} from "../../../Dto/store/ProductDto";
    import TemplateProductUpdateAddView from "./TemplateProductUpdateAddView.svelte";
    import {httpClient} from "../../../Stores/HttpClient";

    let productDto: ProductDto = new ProductDto(ProductDto.emptyDto());
    let client:HttpClient = $httpClient
    let successFlag: boolean  = true;
    const ERR_MSG: string  = "Product creation  failed.";

    function onMountFunction(): void {

    }

    function dispatchFunction(): void {
        alert("dispatch");
        let request:HttpRequest = new HttpRequest(
            PUT,
            "/test-two",
            true,
            ApiService.getDefaultHeaders(),
            productDto
        )
        client.dispatchRequest(request,()=>{},()=>{},processProductDto);
    }

    let props: ViewProps = new ViewProps(
        ViewType.CREATE_VIEW,
        dispatchFunction,
        () => {},
        () => {},
        ()=>{},
        successFlag,
        ERR_MSG
    );
    debugger;
</script>

<TemplateProductUpdateAddView bind:propObject={props} bind:productDto={productDto}/>