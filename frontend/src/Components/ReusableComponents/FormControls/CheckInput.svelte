<script lang="ts">
    export let col:string = "";
    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let labelValue: string = "";
    export let spanValue: string = "";
    export let checkValue: boolean = false;
    export let iconValue: string = "";
    export let onCheck: () => void = () => {};
    export let validator: (arg:any) => void  = (arg:any) => {};

</script>

<style>

</style>

<section class={`form-check form-switch  align-items-start ${col}} form-check `}>
    <input  type=checkbox bind:checked={checkValue} id={CONTROL_ID} class="form-check-input" on:change={onCheck} />
    <label for={CONTROL_ID} class="form-check-label" >{labelValue}</label>
</section>