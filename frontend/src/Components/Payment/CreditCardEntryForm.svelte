<script lang="ts">
    import CreditCardUpdateDto from "./dto/CreditCardUpdateDto";
    import NumberInput from "../ReusableComponents/FormControls/NumberInput.svelte";
    import TextInput from "../ReusableComponents/FormControls/TextInput.svelte";
    import SelectInput from "../ReusableComponents/FormControls/SelectInput.svelte";
    import BaseButton from "../ReusableComponents/Buttons/BaseButton.svelte";
    import ThemeClass from "../ReusableComponents/ThemeClasses/ThemeClass";
    import {HttpClient, HttpRequest, PUT} from "../../Services/HttpClients/HttpClient";
    import ApiService from "../../Services/ApiService/ApiService";
    import {httpClient} from "../../Stores/HttpClient";
    import AcceptedPaymentEnum from "./dto/AcceptedPaymentEnum";
    import {afterUpdate, onMount} from "svelte";
    import paymentStore from "./store/PaymentStore";
    import {PaymentBaseDto} from "./dto/PaymentBaseDto";
    import PaymentMethodStoreDto from "./dto/PaymentMethodStoreDto";

    let client:HttpClient = $httpClient;
    let showErr: boolean = false;

    const COL_CLASS: string = " col-6 ";

    let paymentStoreValue:PaymentMethodStoreDto = $paymentStore;

    //Input properties block
    let selectedPayment: number = AcceptedPaymentEnum.VISA;
    let numberExpirationMonth: number = 1;
    let numberExpirationYear: number = 22;
    let postalCode: number;
    let numberCVV: number = 0;
    let cardName:string = "";
    let numberCardHolderNumber:number = 0;

    //Input properties dto object
    let creditCardDto: CreditCardUpdateDto = new CreditCardUpdateDto();

    //Dynamic properties
    $: cvvMaximum = selectedPayment== AcceptedPaymentEnum.AMEX ? "9999" : "999" ;
    $: creditCardMaximum  =  selectedPayment == AcceptedPaymentEnum.AMEX ? "999999999999999" : "9999999999999999"

    $: cardLabel  =  selectedPayment == AcceptedPaymentEnum.AMEX ? "Enter your fifteen digit card number." :"Enter your sixteen digit card number.";
    $: cardMax = selectedPayment == AcceptedPaymentEnum.AMEX ? "999999999999999" :"9999999999999999";
    $: cvvLabel  =  selectedPayment == AcceptedPaymentEnum.AMEX ? "Enter your three digit security code." :"Enter your four digit security code.";

    function success():void
    {
        let base:PaymentBaseDto = creditCardDto.toPaymentBaseDto();
        $paymentStore.updateSelf(base);
        showErr = false;
        let newStoreValue:PaymentMethodStoreDto =  new PaymentMethodStoreDto(paymentStoreValue);

        paymentStore.set($paymentStore);
        selectedPayment = AcceptedPaymentEnum.VISA;
        numberExpirationMonth = 1;
        numberExpirationYear = 22 ;
        postalCode;
        numberCVV = 0;
        cardName ="";
        numberCardHolderNumber=0;
        syncDto();
    }

    function fail():void
    {
        showErr= true;
    }

    function dispatchRequest():void {
        creditCardDto.paymentType =  selectedPayment;
        let httpRequest:HttpRequest = new HttpRequest(PUT,ApiService.getPaymentTokenUrl(),true,ApiService.getDefaultHeaders(),creditCardDto.toApiDto());
        client.dispatchRequest(httpRequest,()=>success(),()=>fail());
    }

    function cvvValidator():boolean
    {
        let cvvNum:number = creditCardDto.numberCVV;
        let retVal:boolean = false;

        switch (selectedPayment)
        {
            case AcceptedPaymentEnum.VISA:
            case AcceptedPaymentEnum.DISCOVER:
            case AcceptedPaymentEnum.MASTERCARD:
                retVal = cvvNum > 0 && cvvNum < 999;
                break;
            case AcceptedPaymentEnum.AMEX:
                retVal = cvvNum > 0 && cvvNum < 9999;
                break;
        }

        return retVal;
    }

    function creditCardNumberValidator():boolean
    {
        let cvvNum:number = creditCardDto.numberCVV;
        let retVal:boolean = false;

        switch (selectedPayment)
        {
            case AcceptedPaymentEnum.VISA:
            case AcceptedPaymentEnum.DISCOVER:
            case AcceptedPaymentEnum.MASTERCARD:
                retVal = cvvNum > 0 && cvvNum < 999;
                break;
            case AcceptedPaymentEnum.AMEX:
                retVal = cvvNum > 0 && cvvNum < 9999;
                break;
        }

        return retVal;
    }

    function syncDto():void
    {
        creditCardDto.paymentType = selectedPayment;
        creditCardDto.cardName = cardName;
        creditCardDto.numberCardHolderNumber = numberCardHolderNumber;
        creditCardDto.numberCVV = numberCVV;
        creditCardDto.numberExpirationMonth = numberExpirationMonth;
        creditCardDto.numberExpirationYear = numberExpirationYear;
    }

    onMount(syncDto)
    afterUpdate(syncDto);
</script>


<section class="container-fluid bg-white h-100">

    <section class="row h-100  d-flex bg-gradient">
        <p>Creating a new payment card will override any existing one, if present.</p>
            <TextInput  col="col-6"labelValue="Card Name" bind:textValue={cardName} />
            <SelectInput options={AcceptedPaymentEnum.getEnumValueTextArray()} col="col-6" labelValue="Payment Type" bind:selectedValue={selectedPayment}/>
            <NumberInput  col="col-6" labelValue="Card Number" max={cardMax} bind:numberValue={numberCardHolderNumber} spanValue={cardLabel}/>
            <NumberInput  min="0" max="999" col={COL_CLASS} labelValue="CVV " bind:numberValue={numberCVV} spanValue={cvvLabel}/>
            <NumberInput min="1" max="12" col={COL_CLASS} labelValue="Exp.Month" bind:numberValue={numberExpirationMonth}/>
            <NumberInput min="22" max="32" col={COL_CLASS} labelValue="Exp.Yr" bind:numberValue={numberExpirationYear}/>
        <div class="col-12 "></div>
        <section class={" col-12 d-flex flex-row align-items-center justify-content-between"}>
            <p class="text-warning text-muted">
                {#if showErr}
                    Payment didn't go through. Please try again later.
                {/if}
            </p>
            <BaseButton disabled={!creditCardDto.isValid()} onClick={() => dispatchRequest()} value="Update payment" color={ThemeClass.app_control_green}/>
        </section>
    </section>
</section>