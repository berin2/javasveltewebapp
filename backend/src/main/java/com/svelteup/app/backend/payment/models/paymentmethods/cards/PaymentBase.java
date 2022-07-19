package com.svelteup.app.backend.payment.models.paymentmethods.cards;

import com.svelteup.app.backend.payment.models.AcceptedPaymentsEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;



@Embeddable
@Data()
@NoArgsConstructor()
/**
 * PaymentBase is an embeddable sql row representing
 */
public class PaymentBase {
    @Enumerated(EnumType.STRING)
    protected AcceptedPaymentsEnum paymentType;
    protected String cardName;
    protected String paymentToken;
    protected Integer lastFour;

    public PaymentBase(AcceptedPaymentsEnum type, String cardName, String paymentToken,Integer lastFour)
    {
        this.paymentType = type;
        this.paymentToken = paymentToken;
        this.lastFour = lastFour;

        if(cardName == null || "".equals(cardName))
            this.cardName = type.name();
        else
            this.cardName = cardName;

    }
}
