<script lang="ts">
    import CreditCardUpdateDto from "./dto/CreditCardUpdateDto";
    import AcceptedPaymentEnum from "./dto/AcceptedPaymentEnum";
    import CheckInput from "../ReusableComponents/FormControls/CheckInput.svelte";
    import RadioInput from "../ReusableComponents/FormControls/RadioInput.svelte";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import {httpClient} from "../../Stores/HttpClient";
    import {DELETE, HttpClient, HttpRequest} from "../../Services/HttpClients/HttpClient";
    import paymentStore from "./store/PaymentStore";
    import PaymentMethodDeleteDto from "../../Dto/payment/PaymentMethodDeleteDto";
    import ApiService from "../../Services/ApiService/ApiService";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import {PaymentBaseDto} from "../Payment/dto/PaymentBaseDto"
    import PaymentMethodStoreDto from "./dto/PaymentMethodStoreDto";
    import {onMount} from "svelte";
    export let customerPayment: PaymentMethodStoreDto = null;


    let client:HttpClient = $httpClient;
    let showErrMsg: boolean = false;

    function deleteFromPaymentStore(identifier:number)
    {
        switch (identifier)
        {
            case AcceptedPaymentEnum.AMEX:
                customerPayment.amexPaymentMethod =null;
                break;
            case AcceptedPaymentEnum.PAYPAL:
                break;
            case AcceptedPaymentEnum.VISA:
                customerPayment.visaPaymentMethod = null;
                break;
            case AcceptedPaymentEnum.DISCOVER:
                customerPayment.discoverPaymentMethod = null;
                break;
            case AcceptedPaymentEnum.MASTERCARD:
                customerPayment.masterCardPaymentMethod = null;
                break;
        }
    }

    function onSuccess(identifier):void
    {
        deleteFromPaymentStore(identifier)
    }

    function onFail(identifier):void
    {

    }
    function dispatchRequest(identifier:number):void
    {
        let creditCardDeleteDto: CreditCardUpdateDto = new CreditCardUpdateDto();
        creditCardDeleteDto.paymentType = identifier;
        let httpRequest: HttpRequest = new HttpRequest(DELETE,ApiService.getPaymentTokenUrl(),true,ApiService.getDefaultHeaders(),creditCardDeleteDto.toApiDto());
        client.httpRequest(httpRequest,()=> deleteFromPaymentStore(identifier),() => alert("failed"));
    }

</script>



<style>
    .payment-card
    {
        height: 80px;
    }
    .payment-card:hover
    {
        background-color: lightgray !important;
    }
    .payment-img
    {
        min-height: 30px;
        height: auto;
        max-height: 80px;
        width: auto;
        opacity: 0.5;
    }
</style>
<div class="col-5 border-end  d-flex flex-column">
    {#each AcceptedPaymentEnum.getZippedEnumStringImgSrcArray() as entry,index}
        <section class="container fluid border-bottom bg-white h-100 mx-0 px-0 payment-container">
            {#if showErrMsg}
                <section class="row">
                    <p class="col-12 form-text">
                        Payment removal failed. Please try again shortly.
                    </p>
                </section>
            {/if}
        <section class="payment-card  bg-white  d-flex flex-row justify-content-center align-items-center">
            <div  class="mx-1 col-9">
                <img class="payment-img  img-fluid" src={entry["img"]}/>
            </div>
            <div class="col-2">
            </div>
        </section>
            <section class="row">
                    <h6 class="text-secondary text-muted d-flex flex-row justify-content-between">
                        {#if !isFalsy(customerPayment) && !isFalsy(customerPayment.getPaymentMethod(index))}
                            <span class="text-success">
                                 { customerPayment.getPaymentMethod(index).cardName +" ** " + customerPayment.getPaymentMethod(index).lastFour}</span>
                                <BaseButton value="delete" color={ThemeClass.app_control_gray} onClick={()=> dispatchRequest(index)}/>
                        {:else }
                            <span class="text-muted">Payment not saved.</span>
                        {/if}
                    </h6>
            </section>
        </section>
    {/each}
</div>
