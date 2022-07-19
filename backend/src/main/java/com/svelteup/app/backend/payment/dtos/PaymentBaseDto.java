package com.svelteup.app.backend.payment.dtos;

import com.svelteup.app.backend.payment.models.AcceptedPaymentsEnum;
import com.svelteup.app.backend.payment.models.paymentmethods.cards.PaymentBase;
import io.swagger.annotations.ApiModel;

@ApiModel( description = "Represents a saved credit card by the customer.")
public class PaymentBaseDto {
    public String cardName;
    public Integer lastFour;
    public AcceptedPaymentsEnum paymentType;

    public PaymentBaseDto(PaymentBase paymentBase)
    {
        if(paymentBase != null)
        {
            this.cardName = paymentBase.getCardName();
            this.lastFour = paymentBase.getLastFour();
            this.paymentType = paymentBase.getPaymentType();
        }
    }
}
