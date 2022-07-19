<script lang="ts">
    import paymentStore from "./store/PaymentStore";
    import PaymentMethodStoreDto from "./dto/PaymentMethodStoreDto";
    import SelectInput from "../ReusableComponents/FormControls/SelectInput.svelte";
    import AcceptedPaymentsEnum from "./dto/AcceptedPaymentEnum";
    import AcceptedPaymentEnum from "./dto/AcceptedPaymentEnum";
    import {AcceptedPayments} from "./dto/PaymentBaseDto";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";

    let paymentStoreValue: PaymentMethodStoreDto = $paymentStore;


    $: acceptedPaymentEnums = $paymentStore.getAcceptedPaymentsEnumArray();
    $: noPaymentsSaved  = isFalsy(acceptedPaymentEnums.length)
    $: acceptedPaymentEnumsOptions = AcceptedPaymentEnum.getEnumValueTextArrayFromAcceptedPaymentEnumArray(acceptedPaymentEnums);
    $: selectedValue = AcceptedPayments.VISA;


</script>

<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <h1>Preferred Payment</h1>
            <p class="text-muted">
                SvelteUp supports multiple payment providers.
                Your preferred payment type is the payment which you will always
                receive funds from sales. Make sure it is valid and up to date.
            </p>
        </div>
        <div class="col-12">
            {#if !noPaymentsSaved}
                <SelectInput labelValue="Saved Payments"  bind:selectedValue={selectedValue} options={acceptedPaymentEnumsOptions}/>
            {:else}
                <h5 class="text-muted">No payments saved.</h5>
            {/if}
        </div>
        <div class="col-12 d-flex flex-row justify-content-end py-2 ">
            <BaseButton value="UpdatePayment" col={ThemeClass.app_control_green} onClick={()=>{}} disabled={noPaymentsSaved}/>
        </div>
    </div>
</div>