<script lang="ts">
    import {ProductDto, processProductDto} from "../../../Dto/store/ProductDto";
    import {onDestroy, onMount} from "svelte";
    import TextInput from "../../ReusableComponents/FormControls/TextInput.svelte";
    import NumberInput from "../../ReusableComponents/FormControls/NumberInput.svelte";
    import TextArea from "../../ReusableComponents/FormControls/TextArea.svelte";
    import CheckInput from "../../ReusableComponents/FormControls/CheckInput.svelte";
    import ImageGrid from "../../ReusableComponents/LayoutComponents/ImageGrid/ImageGrid.svelte";
    import BaseButton from "../../ReusableComponents/Buttons/BaseButton.svelte";
    import FileUpload from "../../ReusableComponents/FormControls/FileUpload.svelte";
    import ThemeClass from "../../ReusableComponents/ThemeClasses/ThemeClass";
    import {HttpClient, HttpRequest, PUT} from "../../../Services/HttpClients/HttpClient";
    import {httpClient} from "../../../Stores/HttpClient";
    import ComponentH1IconHeader
        from "../../ReusableComponents/LayoutComponents/ComponentHeaders/ComponentH1IconHeader.svelte";
    import ApiService from "../../../Services/ApiService/ApiService";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";
    import ViewType from "../../../Dto/ViewType";
    import ViewProps from "../../ViewProps/ViewProps";
    import {throwError} from "svelte-preprocess/dist/modules/errors";
    import FixedNavButton from "../../ReusableComponents/Buttons/FixedNavButton.svelte";

    export let productDto: ProductDto = null;
    export let propObject: ViewProps = null;
    const INPUT_COL_CLASS: string = " col-6 ";
    const MAXIMUM_IMAGES: number = 4;
    const ICON_CLASS: string  = " bi bi-arrow-left ";
    let headerText: string = null;

    let selectedIndex: number | null = null;
    let client: HttpClient = $httpClient;


    onMount(() => {

        if (isFalsy(propObject))
            throwError("Falsy prop object passed  to Component")
        headerText = propObject.viewType === ViewType.UPDATE_VIEW ? "Update Product" : "Add Product";
        propObject.onMountFunction();
        selectedIndex = 0;
    });

    onDestroy(() => {
        propObject.onDestroyFunction()
    })

    function deleteImageFromArray(): void {
        productDto.productImageStrings.splice(selectedIndex, 1);
        productDto.productImageStrings = [...productDto.productImageStrings];

        if (productDto.productImageStrings.length == 0)
            selectedIndex = null;
        else if (selectedIndex >= productDto.productImageStrings.length)
            selectedIndex = productDto.productImageStrings.length - 1;
    }

    function replaceImageInArray(imagebytes: any): void {
        productDto.productImageStrings[selectedIndex] = imagebytes;
        productDto.productImageStrings = [...productDto.productImageStrings]
    }

    function addImageToArray(imageBytes: any) {
        let dtoValid: boolean = !isFalsy(productDto) && productDto.productImageStrings.length < MAXIMUM_IMAGES;
        if (!isFalsy(imageBytes) && dtoValid) {
            productDto.productImageStrings.push(imageBytes);
            selectedIndex = selectedIndex == null ? 0 : selectedIndex;
            productDto.productImageStrings = [...productDto.productImageStrings];
        }
    }

    function updateProduct(): void {
        let request: HttpRequest = new HttpRequest(PUT, "/testing-two", true, {"content-type": "application/json"}, productDto.toApiDto());
        let imageBuffer: string[] = [];

    }

    function onMountSucc(jsonRes: object) {
        productDto = new ProductDto(jsonRes);
    }

</script>

{#if productDto != null}
    <section class="app-container container bg-white border shadow p-5 ">
        <ComponentH1IconHeader headerText={headerText} iconClass="bi bi-cup-straw"/>
        <section class="row justify-content-center">
            <TextInput col={INPUT_COL_CLASS} labelValue="Product Name" bind:textValue={productDto.productName} />
            <NumberInput col={INPUT_COL_CLASS} labelValue="Product Cost" bind:numberValue={productDto.productCost} stepValue="0.01"/>
            <CheckInput col={INPUT_COL_CLASS} labelValue="Accept Returns" spanValue="Accept Returns for product." bind:checkValue={productDto.productAcceptsReturns} />
            <TextArea col={INPUT_COL_CLASS} labelValue="Product Description" bind:textValue={productDto.productDescription}/>
        </section>
        <section class="row">
            <div>
                selectedindex: {selectedIndex}
            </div>
            <ImageGrid  col=" col-12 " bind:selectedIndex = {selectedIndex} headerText="Product Images" bind:imageByteArray={productDto.productImageStrings}>
                <svelte:fragment slot="slot-one">
                    <FileUpload  col=" col-3 " labelValue="Update Selected Image" spanValue="Replace image" onChange={replaceImageInArray} />
                </svelte:fragment>
                <svelte:fragment slot="slot-two">
                    <section class="col-3 d-flex flex-row justify-content-center align-items-center">
                        <BaseButton col=" col-6 " value="Delete Selected Image" color={ThemeClass.app_outline_control_gray} onClick={() => deleteImageFromArray()}>
                        </BaseButton>
                    </section>
                </svelte:fragment>
                <svelte:fragment slot="slot-three">
                    <section class="col-3 d-flex flex-row justify-content-center align-items-center">
                        <FileUpload col="col-12 " labelValue="Add New Image" spanValue="Upload a new image." color={ThemeClass.app_outline_control_gray} onChange={addImageToArray}/>
                    </section>
                </svelte:fragment>
                <svelte:fragment slot="slot-four">
                    <section class="col-3 d-flex flex-row justify-content-center align-items-center ">
                        <BaseButton value="Update Product" col="" color={ThemeClass.app_outline_control_green} onClick={() => { alert("hiiii!");propObject.dispatchFunction()}}/>
                    </section>
                </svelte:fragment>
            </ImageGrid>
        </section>
    </section>
    <FixedNavButton iconClass={ICON_CLASS} text="Store" route="/store"/>
{/if}