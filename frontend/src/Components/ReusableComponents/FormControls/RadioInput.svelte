<script lang="ts">
    export let col:string = "";
    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let labelValue: string = "";
    export let spanValue: string = "";
    export let radioValue: any = 0;
    export let radioGroup: any = false;
    export let iconValue: string = "";
    export let validator: (arg:any) => void  = (arg:any) => {};
    export let radioIndex = 0;

</script>

<style>

</style>

<section class={`form-check align-items-start ${col}} form-check form-switch`}>
    <input bind:group={radioGroup} type="radio" id={CONTROL_ID} class="form-check text-secondary" value={radioValue} />
    {#if labelValue != ""}
        <label class="form-label" for={CONTROL_ID}>{labelValue}</label>
    {/if}
</section>