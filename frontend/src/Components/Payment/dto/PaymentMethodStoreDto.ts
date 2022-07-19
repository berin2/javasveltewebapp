import {AcceptedPayments, PaymentBaseDto} from "./PaymentBaseDto";
import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";
import AcceptedPaymentsEnum from "./AcceptedPaymentEnum";
import AcceptedPaymentEnum from "./AcceptedPaymentEnum";

class PaymentMethodStoreDto
{
    public  visaPaymentMethod:PaymentBaseDto;
    public  discoverPaymentMethod:PaymentBaseDto;
    public  amexPaymentMethod:PaymentBaseDto;
    public  masterCardPaymentMethod:PaymentBaseDto;
    public preferredPaymentAcceptedType:AcceptedPayments;

    public constructor(arg:object)
    {
        if(!isFalsy(arg["visaPaymentMethod"]))
            this.visaPaymentMethod  = arg["visaPaymentMethod"];

        if(!isFalsy(arg["discoverPaymentMethod"]))
            this.discoverPaymentMethod  = arg["discoverPaymentMethod"];

        if(!isFalsy(arg["amexPaymentMethod"]))
            this.amexPaymentMethod  = arg["amexPaymentMethod"];

        if(!isFalsy(arg["masterCardPaymentMethod"]))
            this.masterCardPaymentMethod  = arg["masterCardPaymentMethod"];

        if(!isFalsy("preferredPaymentAcceptedType"))
            this.preferredPaymentAcceptedType = arg["preferredPaymentAcceptedType"];
    }

    public updateSelf(paymentMethod:PaymentBaseDto):void
    {
        switch (paymentMethod.paymentType)
        {
            case AcceptedPayments.AMEX:
                this.amexPaymentMethod = paymentMethod;
                break;
            case AcceptedPayments.DISCOVER:
                this.discoverPaymentMethod = paymentMethod;
                break;
            case AcceptedPayments.VISA:
                this.visaPaymentMethod = paymentMethod;
                break;
            case AcceptedPayments.MASTERCARD:
                this.masterCardPaymentMethod = paymentMethod;
                break;
        }

        if(this.preferredPaymentAcceptedType == null)
            this.updatePreferredPaymentMethodType(paymentMethod.paymentType);
    }

    public updatePreferredPaymentMethodType(preferredPaymentMethod:number)
    {
        switch (preferredPaymentMethod)
        {
            case AcceptedPayments.AMEX:
                if(this.amexPaymentMethod != null)
                    this.preferredPaymentAcceptedType = AcceptedPayments.AMEX;
                break;
            case AcceptedPayments.VISA:
                if(this.visaPaymentMethod != null)
                    this.preferredPaymentAcceptedType = AcceptedPayments.VISA;
                break;
            case AcceptedPayments.MASTERCARD:
                if(this.masterCardPaymentMethod != null)
                    this.preferredPaymentAcceptedType = AcceptedPayments.MASTERCARD;
                break;
            case AcceptedPayments.DISCOVER:
                if(this.discoverPaymentMethod != null)
                    this.preferredPaymentAcceptedType = AcceptedPayments.DISCOVER;;
                break;
        }
    }

    public getPaymentMethod(paymentMethod:number):PaymentBaseDto
    {
        let retVal:PaymentBaseDto = null;

        switch (paymentMethod)
        {
            case AcceptedPayments.AMEX:
                retVal = this.amexPaymentMethod;
                break;
            case AcceptedPayments.DISCOVER:
                retVal = this.discoverPaymentMethod;
                break;
            case AcceptedPayments.VISA:
                retVal = this.visaPaymentMethod;
                break;
            case AcceptedPayments.MASTERCARD:
                retVal=this.masterCardPaymentMethod;
                break;
        }

        return retVal;
    }

    public getAcceptedPaymentsEnumArray(): AcceptedPaymentEnum[]
    {
       let returnArray: AcceptedPaymentEnum [] = [];

       if(!isFalsy(this.amexPaymentMethod))
           returnArray.push(AcceptedPaymentEnum.AMEX)
       if(!isFalsy(this.visaPaymentMethod))
           returnArray.push(AcceptedPaymentEnum.VISA)
        if(!isFalsy(this.discoverPaymentMethod))
            returnArray.push(AcceptedPaymentEnum.DISCOVER)
        if(!isFalsy(this.masterCardPaymentMethod))
            returnArray.push(AcceptedPaymentEnum.MASTERCARD)

        return returnArray;
    }
}

export default PaymentMethodStoreDto;