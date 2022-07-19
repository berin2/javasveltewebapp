<script lang="ts">
    import {range} from "../../Services/Utils/ArrayUtils";
    import {fade} from "svelte/transition";

    export let containingElementHeight = 200;
    export let rowHeight:number = 100;
    export let showSpinner: boolean = true;
    export let spinnerActive:boolean = true;


    $: dynamicCount = Math.floor(containingElementHeight / rowHeight);
    $: count = dynamicCount > 1?  dynamicCount - 1 : 1;

</script>

<style>
    .color-styling
    {
        background-color: #f0f0f0;
        border:1px solid #d6d6d6;
        height:65px;
        width:100%;
    }

    .inner-content-styling
    {
        background-color: #d6d6d6;
        height: 40px;
        width: 40px;
    }

    .inner-content-styling-row
    {
        background-color: #d6d6d6;
        border-radius: 4px;
        height: 10px;
        width: 100%;
    }
</style>
{#each range(count) as element}
    <div class="color-styling container-fluid w-100 my-2" transition:fade={{duration:200}}>
            <div class="row h-100 p-2 ">
                    <div class="col-4 d-flex flex-row justify-content-center align-items-center">
                        <div class="inner-content-styling img-thumbnail"> </div>
                    </div>
                    <div class="col-8 d-flex flex-column justify-content-around">
                        <div class="inner-content-styling-row"></div>
                        <div class="inner-content-styling-row"></div>
                        <div class="inner-content-styling-row"></div>
                    </div>
            </div>
    </div>
{/each}
{#if showSpinner}
    {#if spinnerActive}
        <div class="text-center">
         <div class="spinner-border text-success" role="status"></div>
        </div>
    {/if}
{/if}
