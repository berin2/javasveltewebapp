<script lang="ts">
    import {onMount} from "svelte";
    import ThemeClass from "../../ThemeClasses/ThemeClass";

    export let firstLetter: string = "T";
    export let height: number = 80;

    let style:string = "";
    let bgClass:string = "";


    onMount(()=>{
        // height set to invalid value, set to default of 80.
        if(height < 0) height = 80;
        style =  `height:${height}px;width:${height}px`;
        let randNum: number = ( Math.floor(Math.random() * 1000));

        if(randNum % 3 == 0) bgClass= " bg-secondary ";
        else if(randNum % 2 == 0) bgClass = " bg-info ";
        else bgClass = " bg-success ";
    });

</script>
<style>
    .circle-image-wrapper
    {
        border-radius:100%;
        overflow: hidden;
    }
</style>

<div class={`circle-image-wrapper d-flex flex-row justify-content-center align-items-center m-1 bg-secondary ${bgClass}`} style={style}>
    <h1 class=" text-white d-flex flex-column ">
        {firstLetter}
    </h1>
</div>
