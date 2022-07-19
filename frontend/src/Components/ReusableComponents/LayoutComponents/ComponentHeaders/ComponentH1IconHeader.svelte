<script lang="ts">
    export let headerText:string|null = null;
    export let headerClass:string|null = null;
    export  let iconClass:string|null = null;
</script>

<style>
    .icon-size-class {font-size: 2.5em;}
</style>

<section class="row d-flex flex-row align-items-center justify-content-between my-1">
    <section class="col-12 d-flex flex-row justify-content-between">
        <h1>{headerText}</h1>
        {#if iconClass !== null}
            <i class={iconClass + " icon-size-class "}></i>
        {/if}
    </section>
</section>