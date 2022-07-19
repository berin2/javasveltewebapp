package com.svelteup.app.backend.payment.dtos;

import com.svelteup.app.backend.payment.models.AcceptedPaymentsEnum;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.payment.models.paymentmethods.cards.PaymentBase;
import io.swagger.annotations.ApiModel;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@ApiModel(description = "Represents JSON used to transfer registered payment methods of the customer.")
public class PaymentMethodStoreDto {
    public PaymentBaseDto visaPaymentMethod;
    public PaymentBaseDto discoverPaymentMethod;
    public PaymentBaseDto amexPaymentMethod;
    public PaymentBaseDto masterCardPaymentMethod;
    public AcceptedPaymentsEnum preferredPaymentAcceptedType;

    public PaymentMethodStoreDto()
    {

    }
}
