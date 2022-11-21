
<script lang="ts">
    import ThemeClass from "../ThemeClasses/ThemeClass";

    export let onClick =  (e) => alert("button is not implemented.");
    export let color:string = ThemeClass.app_control_green;
    export let value:string = "";
    export let col:string  = "";
    export let marginPadding: string = "";
    export let disabled: boolean = false;
    let BUTTON_CLASS =  color + " " + col + " " + marginPadding;
</script>
<button class={BUTTON_CLASS} on:click={(e) => onClick(e)} disabled={disabled}>
    {value}
    <slot name="icon"></slot>
</button>