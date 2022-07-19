import BaseDto from "../BaseDto";
import type ToApiDto from "../ToApiDto";

class PaymentMethodDeleteDto implements ToApiDto
{
    public paymentMethodToDelete:string;
    constructor(paymentMethodToDelete:string)
    {
        this.paymentMethodToDelete = paymentMethodToDelete;}

    toApiDto(): object {
        return this;
    }
}

export default PaymentMethodDeleteDto;