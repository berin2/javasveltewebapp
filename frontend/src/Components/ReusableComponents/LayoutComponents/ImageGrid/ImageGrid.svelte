<script lang="ts">
    import {isFalsy} from "../../../../Validators/IsFalsyObjectValidator";
    import ComponentH1IconHeader from "../ComponentHeaders/ComponentH1IconHeader.svelte";
    import {onMount} from "svelte";

    export let imageByteArray: string [] = []
    export let imageClass: string = " img-fluid ";

    export let col = " col-12 ";
    export let imageGridId: string = String(Math.random() * Math.random()) + "-image-carousel";
    export let iconClass: string | null = null;
    export let containerClass: string | null = null;
    export let headerText:string | null  = null;
    export let selectedIndex: number = 0;


    $:selectedImage =  !isFalsy(imageByteArray[selectedIndex]) ? imageByteArray[selectedIndex] : null;


    const CHEVRON_COL: string = " col-1 d-flex flex-column justify-content-center align-items-center "

    $: nextEnabled = !isFalsy(imageByteArray) && selectedIndex < imageByteArray.length - 1 ? true : false;
    $: prevEnabled = !isFalsy(imageByteArray) && selectedIndex > 0 ? true : false;

    $: nextColClass = nextEnabled ? " grid-chevron-col " : " grid-chevron-col-disabled ";
    $: prevColClass = prevEnabled ? " grid-chevron-col " : " grid-chevron-col-disabled ";

    $: nextChevronClass = nextEnabled ? " bi bi-chevron-right text-success fw-bold grid-chevron " : " bi bi-dash-lg grid-chevron-disabled text-secondary ";
    $: prevChevronClass = prevEnabled ? " bi bi-chevron-left text-success fw-bold grid-chevron " : " bi bi-dash-lg grid-chevron-disabled text-secondary ";

    $:selectedImage = !isFalsy(imageByteArray) && !isFalsy(imageByteArray.length)
        ?  imageByteArray[selectedIndex] : null;

    function incrementIndex() {
        if(nextEnabled) selectedIndex = selectedIndex +  1;
    }

    function decrementIndex() {if(prevEnabled) selectedIndex  = selectedIndex  - 1;}

    function updateIndex(): void
    {
        if(isFalsy(imageByteArray))
            throw Error("imageByteArray exptected to be non falsy but was discovered falsy.");

        if(isFalsy(imageByteArray.length))
            selectedIndex = 0;

    }
</script>

<style>
    .carousel-view {
        background-color: white !important;
        height: 400px !important;
        width: 400px !important;
    }

    .image-display-row {
        height: 400px !important;
    }

    .grid-image {
        aspect-ratio: 1/1 !important;
        object-fit: contain !important;
        width: 100% !important;
    }
    .img-tab-row {
        height: 90px;
        overflow-x: scroll !important;
    }
    .img-tab
    {
        aspect-ratio: 1/1 !important;
        height: 30px;
        width: 30px;
    }

    .img-not-found-icon
    {
        font-size: 4em;
    }

    .grid-chevron {
        font-size: 3.0em;
    }

    .grid-chevron-col
    {


    }

    .grid-chevron-col-disabled {
        background-color: #cccccc !important;
    }

    .grid-chevron-disabled {
        color: white;
        font-size: 1.5em;
    }

    .img-render-wrapper
    {
        width: 370px !important;
        height: 370px !important;
    }

    @keyframes bg-switch {
        from{background-color: white }
        to {background-color: #cccccc}
    }
</style>


<section class="container bg-white image-display-container ">
    <section class="row">
        <div class="col-12 d-flex flex-row justify-content-between align-items-center ">
            {#if headerText != null}
                <h1>{headerText}</h1>
            {/if}
            {#if iconClass !== null}
                <i class={iconClass}></i>
            {/if}
        </div>
    </section>
   <section class="row mx-y image-display-row">
       <section class={ CHEVRON_COL +  prevColClass} on:click|preventDefault={decrementIndex}>
           <i class={prevChevronClass}></i>
       </section>
        <section class="col-10 d-flex flex-row justify-content-center  align-items-center image-display-row">
            {#if  !isFalsy(selectedImage)}
                <div class=" img-render-wrapper p-4 ">
                    <img  class="grid-image" src={selectedImage} alt="No image found." />
                </div>
            {:else}
                <i class="bi bi-file-image-fill card-icon text-secondary fa-10x img-not-found-icon"></i>
            {/if}
        </section>
       <section class={CHEVRON_COL  +  nextColClass} on:click|preventDefault={incrementIndex}>
           <i class={nextChevronClass} ></i>
       </section>   </section>
    <div class="row  d-flex flex-row  flex-nowrap py-3 img-tab-row border-top">
        {#each imageByteArray as image, i}
            <button class="img-tab btn btn-outline-success rounded border d-flex flex-row justify-content-center align-items-center m-1" on:click={() => {selectedIndex = i;}}>
                {i + 1}
            </button>
        {/each}
    </div>
    <section class="row bg-white my-2">
        <slot name="slot-one">
        </slot>
        <slot name="slot-two">
        </slot>
        <slot name="slot-three">
        </slot>
        <slot name="slot-four">
        </slot>
    </section>
</section>
