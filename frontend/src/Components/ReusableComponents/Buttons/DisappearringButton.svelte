<script lang="ts">
    import BaseButton from "./BaseButton.svelte";

    export let onClick = (e) => alert("button is not implemented.");
    export let color: string = "";
    export let value: string = "";
    export let hidden:boolean = false;
</script>

<span hidden ={hidden}>
    <BaseButton  color={color} value={value} onClick={onClick} />
</span>