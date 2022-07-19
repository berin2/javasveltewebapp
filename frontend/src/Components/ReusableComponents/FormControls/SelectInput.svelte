<script lang="ts">

    export let col:string = "";
    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let selectedValue: any = "";
    export let spanValue: string = "";
    export let labelValue:string =""
    export let options:any[] = [];
    export let iconValue: string = "";
    export let validator: (arg:any) => void  = (arg:any) => {};

</script>

<style>

</style>


<section class={`form-group d-flex flex-column align-items-start ${col} `}>
    <label for={CONTROL_ID} class=" w-100 form-label d-flex flex-row justify-content-between ps-1">{labelValue} <span class={iconValue + " ms-auto"}></span> </label>
    <select bind:value={selectedValue} class="form-select" id={CONTROL_ID} >
        {#each options as option}
            <option value={option.value}>{option.text}</option>
        {/each}
    </select>
    <span class="form-text text-muted ps-1">{spanValue}</span>
</section>