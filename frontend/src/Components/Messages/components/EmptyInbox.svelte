<script lang="ts">
    import PlaceholderRectangle from "../../LoadingPlaceHolders/PlaceholderRectangle.svelte";
    import {fade} from "svelte/transition";
    export let containingElementHeight = 200;
</script>
<style></style>

<div class="h-100 d-flex flex-column justify-content-center" transition:fade={{duration:500}}>
    <h3 class=" m-2 text-center border-top my-2 text-secondary ">Such Empty</h3>
    <PlaceholderRectangle showSpinner={false} bind:containingElementHeight={containingElementHeight} />
    <p class=" text-center border-bottom"> It's a little quiet around here :( </p>
    <button class="btn btn-success m-4">
      New Chat
        <i class="bi bi-plus-circle"></i>
    </button>
</div>