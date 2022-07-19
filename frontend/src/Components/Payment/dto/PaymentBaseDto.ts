import CreditCardUpdateDto from "./CreditCardUpdateDto";

class PaymentBaseDto
{
    public cardName:string;
    public lastFour:number;
    public paymentType:AcceptedPayments;

    constructor(
            arg:object
        )
    {
        this.cardName = arg["cardName"];
        this.lastFour =arg["lastFour"];
        this.paymentType = arg["paymentType"];
    }
}

enum AcceptedPayments {
    VISA,
    MASTERCARD,
    DISCOVER,
    AMEX,
    PAYPAL,
    NOTADDED
}

class CustomerPaymentInfoDto
{
    public amexPaymentMethodName:PaymentBaseDto;
    public discoverPaymentMethodName:PaymentBaseDto;
    public masterCardPaymentMethodName:PaymentBaseDto;
    public visaPaymentMethodName:PaymentBaseDto;

    constructor(arg:object) {
        this.amexPaymentMethodName = arg["amexPaymentMethodName"];
        this.discoverPaymentMethodName = arg["discoverPaymentMethodName"];
        this.masterCardPaymentMethodName = arg["masterCardPaymentMethodName"];
        this.visaPaymentMethodName = arg["visaPaymentMethodName"];
    }
}

export {PaymentBaseDto,CustomerPaymentInfoDto,AcceptedPayments}
