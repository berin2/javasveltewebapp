<script lang="ts">
    export let onChange: any = () => alert("form control not implemented.");
    export let col:string = "";
    const CONTROL_ID: string = (Math.random() * Math.random() * Math.random()).toString();

    export let labelValue: string = "";
    export let placeholderValue: string = "";
    export let spanValue: string = "";
    export let numberValue: number = 0;
    export let iconValue: string = "";
    export let stepValue:number = 1;
    export let disabled: boolean = false;
    export let min:number = 0;
    export let max:number = 99999;
    export let validator: (arg:any) => void  = (arg:any) => {return false};
    export let validatorMsg: string|null;
</script>

<style>

</style>

<section class={`form-group d-flex flex-column align-items-start m-1  ${col}}`}>
    <label for={CONTROL_ID} class=" w-100 form-label d-flex flex-row justify-content-between ps-1"> {labelValue} <span class={iconValue + " ms-auto"}></span> </label>
    <input disabled={disabled} min={min} max={max} type="number" step={stepValue} on:change={(e) => validator(e.target.value)} bind:value={numberValue} id={CONTROL_ID} class="form-control" placeholder={placeholderValue}/>
    {#if spanValue != ""}
        <span class=" form-text text-muted">{spanValue}</span>
    {/if}
    {#if validatorMsg != null  && !validator()}
        <span class=" form-text text-warning">{validatorMsg}</span>
    {/if}
</section>