<script>
    import PlaceholderSquare from "../../LoadingPlaceHolders/PlaceholderSquare.svelte";
</script>
<section class=" col-12 text-secondary text-center my-3">
    {#if true}
        {#if true}
            <div class="text-center">
                <div class="spinner-border text-success" role="status" style="width: 5rem;height: 5rem;"></div>
            </div>
        {/if}
    {/if}
    <h1 class="text-secondary">Hang tight, we're getting your store ready.</h1>

</section>
{#each Array(4) as _,i}
    <section class="col-6 p-4">
        <PlaceholderSquare containingElementHeight={250}/>
    </section>
{/each}