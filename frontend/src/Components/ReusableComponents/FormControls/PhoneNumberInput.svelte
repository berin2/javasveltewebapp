<script lang="ts">
    import NumberInput from "./NumberInput.svelte";
    import {PhoneViewTypeEnum} from "../../../Datastructures/EnumClasses";
    export let areaCode: number;
    export let phoneInput: number;
</script>



<span class="d-flex flex-row align-items-center justify-content-center">
    <NumberInput min={1} max={999}  stepValue="1" bind:numberValue={areaCode} labelValue={"Area Code"} />
    <NumberInput  min={1000000} max={9999999} stepValue="1" bind:numberValue={areaCode} labelValue={"Phone Number"} />
</span>
