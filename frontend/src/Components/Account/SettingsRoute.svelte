<script lang="ts">
    import {throwError} from "svelte-preprocess/dist/modules/errors";

    export let routeTitle:String = "Default Title";
    export let transitionFunction: () => void = () => throwError("Expected transition function passed in transitionFunction but none provided.");

</script>

<style>
    .hover-row{}
    .hover-row:hover
    {
        background-color: #8f8f8f !important;
        color: white !important;
    }
</style>
<section class="col-12 d-flex flex-row justify-content-between align-items-center p-3 border-bottom hover-row rounded" on:click|preventDefault={transitionFunction}>
    <h5>{routeTitle}</h5>
    <h5 class=" bi bi-arrow-right "></h5>
</section>