<script lang="ts">
    import {fade} from "svelte/transition";
    export let isValid:boolean|null = null;
    export let isValidText:string = "";
    export let isNotValidText:string = "";
    export let validatorClass= " "
</script>

{#if isValid != null}
    <p class={" text-success " + validatorClass} transition:fade={{duration:500}} >
    {#if isValid}
        {isValidText}
    {:else if !isValid}
        {isNotValidText}
    {/if}
    </p>
{/if}