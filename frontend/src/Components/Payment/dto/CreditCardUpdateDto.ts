import type ToApiDto from "../../../Dto/ToApiDto";
import  AcceptedPaymentsEnum from "./AcceptedPaymentEnum";
import AcceptedPaymentEnum from "./AcceptedPaymentEnum";
import {PaymentBaseDto} from "./PaymentBaseDto";

class CreditCardUpdateDto implements ToApiDto
{
    public numberExpirationMonth: number;
    public numberExpirationYear: number;
    public expirationMonth: string;
    public expirationYear: string;
    public postalCode: number;
    public numberCVV: number;
    public CVV:string;
    public cardName:string;
    public paymentType: number;
    public numberCardHolderNumber:number;
    public cardHolderNumber:string;

    constructor() {
        this.numberExpirationMonth = 1;
        this.numberExpirationYear = 22;
        this.postalCode = 0;
        this.cardName = "";
        this.paymentType = AcceptedPaymentsEnum.NOTADDED;
        this.numberCardHolderNumber = 0;
    }

    toApiDto(): object
    {
        this.CVV =  this.produceCVV();
        this.cardHolderNumber = this.produceCardNumber();
        this.expirationMonth = this.producePadding(this.numberExpirationMonth,2);
        this.expirationYear =  this.producePadding(this.numberExpirationYear,2);
        return this;
    }

    protected produceStringMonth():string
    {
        return this.producePadding(this.numberExpirationMonth,2);
    }

    protected produceStringYear():string
    {
        return this.producePadding(this.numberExpirationYear,2);
    }
    protected produceCVV():string
    {
        let retVal:string = "0";

        switch (this.paymentType)
        {
            case AcceptedPaymentEnum.VISA:
            case AcceptedPaymentEnum.DISCOVER:
            case AcceptedPaymentEnum.MASTERCARD:
                    retVal = this.producePadding(this.numberCVV,3);
                break;
            case AcceptedPaymentEnum.AMEX:
                retVal = this.producePadding(this.numberCVV,4);
                break;
        }

        return retVal;
    }

    protected producePadding(num:number, paddingLimitLength:number)
    {
        let numStr:string = num + "";
        let retStr:string = "";
        if(numStr.length < paddingLimitLength)
            for(let i = 0 ; i < paddingLimitLength - numStr.length ; i++)
                retStr = "0" + retStr ;

        retStr = retStr + numStr;

        return retStr;
    }

    protected produceLastFour(): string
    {
        let cardHolderNumberString: string = this.numberCardHolderNumber + "";
        if(cardHolderNumberString.length > 4)
            cardHolderNumberString  = cardHolderNumberString.slice(-4);
        else
        {
            cardHolderNumberString = this.producePadding(this.numberCardHolderNumber,4 - cardHolderNumberString.length);
        }

        return cardHolderNumberString;
    }

    protected  produceCardNumber():string
    {
        let cvvNum:number = this.numberCardHolderNumber;
        let retVal:string = "0";

        switch (this.paymentType)
        {
            case AcceptedPaymentEnum.VISA:
            case AcceptedPaymentEnum.DISCOVER:
            case AcceptedPaymentEnum.MASTERCARD:
                retVal = this.producePadding(this.numberCardHolderNumber,16);
                break;
            case AcceptedPaymentEnum.AMEX:
                retVal = this.producePadding(this.numberCardHolderNumber,15);
                break;
        }

        return retVal;
    }

    isValid():boolean
    {
        return true;
    }

    public toPaymentBaseDto():PaymentBaseDto
    {
        let argObject = {
            cardName: this.cardName,
            lastFour: this.produceLastFour(),
            paymentType: this.paymentType
        }
        let retVal:PaymentBaseDto = new PaymentBaseDto(argObject);

        return retVal;
    }
}

export default CreditCardUpdateDto;