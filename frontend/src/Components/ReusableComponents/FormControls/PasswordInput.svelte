<script lang="ts">
    export let onChange: any = () => alert("form control not implemented.");
    export let col:string = "";
    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let labelValue: string = "";
    export let placeholderValue: string = "";
    export let spanValue: string = "";
    export let passwordValue: string = "";
    export let iconValue: string = "";
</script>

<style>

</style>

<section class={`form-group d-flex flex-column align-items-start ${col}}`}>
    <label for={CONTROL_ID} class=" w-100 form-label d-flex flex-row justify-content-between ps-1">{labelValue} <span class={iconValue + " ms-auto"}></span> </label>
    <input type="password" bind:value={passwordValue} id={CONTROL_ID} class="form-control" placeholder={placeholderValue}/>
    <span class="form-text text-muted ps-1">{spanValue}</span>
</section>