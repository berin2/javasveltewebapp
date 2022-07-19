<script lang="ts">
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import CreditCardEntryForm from "./CreditCardEntryForm.svelte";
    import {PaymentBaseDto} from "./dto/PaymentBaseDto";

    export let paymentCard: PaymentBaseDto = null;
    export let paymentType: number = -1;
</script>

<style>
    .icon-image
    {
        height: auto;
        max-height: 400px;
        min-height: 300px;
        width: auto;
        opacity: .5;
    }
</style>
<section class="h-100 d-flex flex-column align-content-around justify-content-between px-0 pl">
    <CreditCardEntryForm/>
</section>