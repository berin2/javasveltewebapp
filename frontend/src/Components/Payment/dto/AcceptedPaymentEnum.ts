import {AcceptedPayments} from "./PaymentBaseDto";

class AcceptedPaymentsEnum {
    public static readonly VISA = 0;
    public static readonly MASTERCARD = 1;
    public static readonly DISCOVER = 2;
    public static readonly AMEX=3;
    public static readonly PAYPAL=4;
    public static readonly NOTADDED=5;
    static readonly  ARRAY_ENDING_INDEX = 5;


    public static getZippedEnumStringImgSrcArray(): any [] {
        let retArr = []
        for(let i = 0; i < AcceptedPaymentsEnum.ARRAY_ENDING_INDEX; i++) {
            retArr[i] = {
                "img":AcceptedPaymentsEnum.getEnumIconImageStringFromNumber(i),
                "text": AcceptedPaymentsEnum.getEnumStringValueFromNumber(i)
            };
        }
        return retArr;
    }
    public static getEnumStringArray = function ():string[] {
        return [
            AcceptedPaymentsEnum.getEnumStringValueFromNumber(AcceptedPaymentsEnum.VISA),
            AcceptedPaymentsEnum.getEnumStringValueFromNumber(AcceptedPaymentsEnum.MASTERCARD),
            AcceptedPaymentsEnum.getEnumStringValueFromNumber(AcceptedPaymentsEnum.DISCOVER),
            AcceptedPaymentsEnum.getEnumStringValueFromNumber(AcceptedPaymentsEnum.AMEX),
            AcceptedPaymentsEnum.getEnumStringValueFromNumber(AcceptedPaymentsEnum.PAYPAL)
        ];
    }

    public static getEnumValueTextArrayFromAcceptedPaymentEnumArray(array: number[]): object[]
    {
        let retArray:object[] = [];
        for(let paymentValue of array)
            retArray.push({"value":paymentValue,"text":this.getEnumStringValueFromNumber(paymentValue)});


        return retArray;
    }
    public static getEnumValueTextArray() : object[] {
        return [
            {
                "value": AcceptedPayments.VISA,
                "text": this.getEnumStringValueFromNumber(AcceptedPayments.VISA)
            },
            {
                "value": AcceptedPayments.PAYPAL,
                "text": this.getEnumStringValueFromNumber(AcceptedPayments.PAYPAL)
            },
            {
                "value": AcceptedPayments.AMEX,
                "text": this.getEnumStringValueFromNumber(AcceptedPayments.AMEX)
            },
            {
                "value": AcceptedPayments.DISCOVER,
                "text": this.getEnumStringValueFromNumber(AcceptedPayments.DISCOVER)
            },
            {
                "value": AcceptedPayments.NOTADDED,
                "text": this.getEnumStringValueFromNumber(AcceptedPayments.NOTADDED)
            },
            {
                "value": AcceptedPayments.MASTERCARD,
                "text": this.getEnumStringValueFromNumber(AcceptedPayments.MASTERCARD)
            },
        ];
    }

    public static getEnumStringValueFromNumber(value:number):string
    {
        let retValue:string = "NOT_ADDED";

        switch (value)
        {
            case this.AMEX:
                retValue = "AMEX"
                break;
            case this.VISA:
                retValue = "VISA";
                break;
            case this.MASTERCARD:
                retValue = "MASTERCARD";
                break;
            case this.DISCOVER:
                retValue = "DISCOVER";
                break;
            case this.PAYPAL:
                retValue = "PAYPAL";
                break;
        }
        return  retValue;
    }

    public static getEnumIconImageStringFromNumber(value:number):string
    {
        let retValue:string  = "";

        switch (value)
        {
            case this.AMEX:
                retValue = "/images/amex.png"
                break;
            case this.VISA:
                retValue = "/images/visa.png";
                break;
            case this.MASTERCARD:
                retValue = "/images/mastercard.png";
                break;
            case this.DISCOVER:
                retValue = "/images/discover.png";
                break;
            case this.PAYPAL:
                retValue = "/images/paypal.png";
                break;
        }
        return retValue;
    }
}


export default AcceptedPaymentsEnum;
