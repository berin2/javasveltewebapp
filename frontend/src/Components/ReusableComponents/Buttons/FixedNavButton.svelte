<script lang="ts">
    import {navigate} from "svelte-navigator";
    import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

    export let route:  string | null= null;
    export let iconClass: string|null= null;
    export let text: string|null =  null;

    function navigateToRoute():void { navigate(route);}
</script>

<style>
    .bottom-left-nav-button-wrapper
    {
        bottom: 0 !important;
        left: 0 !important;
        position:fixed !important;
        z-index: 9888  !important;
    }

    .bottom-left-floating-nav-button
    {
        bottom: 20px;
        border: 20px;
    }
</style>

<div class="form-group bottom-left-nav-button-wrapper m-4">
    <button class="bottom-left-floating-nav-button btn btn-success shadow" on:click|preventDefault={()  => navigateToRoute()}>
         {#if !isFalsy(iconClass)}
             <i class={iconClass}></i>
         {/if}
        {text}
    </button>
</div>