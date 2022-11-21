<script lang="ts">
    import {onMount} from "svelte";
    import ProfileLetterComponent from "./ProfileLetterComponent.svelte";
    import ImageService from "../../../../Services/Utils/ImageService";

    export let src: string = "";
    export let height: number = 80;
    export let firstLetter: string = "-";
    let style: string = "";


    onMount(() => {
        // height set to invalid value, set to default of 80.
        if (height < 0) height = 80;
        style = `height:${height}px;width:${height}px`;
    });
</script>

<style>
    .circle-image-wrapper {
        border-radius:100%;
        overflow: hidden;
    }
</style>

    {#if ImageService.validatePngOrJpeg(src)}
        <div class="circle-image-wrapper d-flex flex-row justify-content-center align-items-center m-1 bg-secondary" style={style}>
            <img class="h-100" src={src}/>
        </div>
    {:else}
        <slot name="body">
            <ProfileLetterComponent firstLetter={firstLetter}/>
        </slot>
    {/if}

