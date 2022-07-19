<script  lang="ts">

    import {httpClient} from "../../Stores/HttpClient";
    import paymentStore from "./store/PaymentStore";
    import {GET, HttpClient, HttpRequest} from "../../Services/HttpClients/HttpClient";
    import {isFalsy} from "../../Validators/IsFalsyObjectValidator";
    import PaymentOptions from "./PaymentOptions.svelte";
    import AcceptedPaymentEnum from "./dto/AcceptedPaymentEnum";
    import PaymentCardView from "./PaymentCardView.svelte";
    import ApiService from "../../Services/ApiService/ApiService";
    import {onMount} from "svelte";
    import PaymentMethodStoreDto from "./dto/PaymentMethodStoreDto";
    import PreferredPaymentType from "./PreferredPaymentType.svelte";

    let selectedPaymentType: AcceptedPaymentEnum = AcceptedPaymentEnum.AMEX;
    let customerPayment: PaymentMethodStoreDto = $paymentStore;
    let client: HttpClient = $httpClient;

    function onSuccess(response: object): void {
        customerPayment = new PaymentMethodStoreDto(response);
        paymentStore.set(customerPayment);
    }

    function onFail(): void {

    }

    function processDto(arg: object): object {
        if (isFalsy(arg))
            arg = {};
        return arg;
    }

    function dtoProcessFunction(arg: object): object {
        return arg;
    }

    function onMountData() {
        let httpRequest: HttpRequest = new HttpRequest(GET, ApiService.getPaymentTokenUrl(), true, ApiService.getDefaultHeaders());
        client.httpRequest(httpRequest, onSuccess, onFail, processDto);
    }

    onMount(() => onMountData());
</script>

<style>
    .payment-view
    {
        height:400px;
    }

</style>

<section class="app-container app-bar-margin ">
    <div class="container payment-view bg-white ">
        <div class="row shadow py-1 payment-container app-container-border-gray">
            <h1 class="col-12  border-bottom">Payment Management</h1>
            <PaymentOptions bind:customerPayment={$paymentStore}/>
            <div class="col-7 px-0 mx-0">
                <PaymentCardView/>
            </div>
            <div class="col-12 bg-white border-top">
                <PreferredPaymentType/>
            </div>
        </div>
    </div>
</section>