
<script lang="ts">
    export let onClick =  (e) => alert("button is not implemented.");
    export let color:string = "";
    export let value:string = "";
    export let col:string  = "";
    export let marginPadding: string = "";
    export let disabled: boolean = false;
    let BUTTON_CLASS =  color + " " + col + " " + marginPadding;
</script>
<input type="button" class={BUTTON_CLASS} value={value} on:click={(e) => onClick(e)} disabled={disabled}/>