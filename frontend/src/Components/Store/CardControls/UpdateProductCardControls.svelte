<script lang="ts">
    import BaseButton from "../../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../../ReusableComponents/ThemeClasses/ThemeClass";
    export let onClickDeleteProduct: () => void;
    export let onClickUpdateProduct: () => void;
    export let onClickViewProduct: () => void;
    export let productSurrogateId: string;

</script>
<style></style>

<BaseButton value="View" color={ThemeClass.app_outline_control_green} col="col-3" marginPadding="m-1 p-1" onClick={onClickViewProduct}/>
<BaseButton value="Update" color={ThemeClass.app_outline_control_gray} col="col-3" marginPadding="m-1 p-1" onClick={onClickUpdateProduct}/>
<BaseButton value="Delete" color={ThemeClass.app_outline_control_gray} col="col-3" marginPadding="m-1 p-1" onClick={onClickDeleteProduct}/>
