package com.svelteup.app.backend.payment.dtos;

import com.svelteup.app.backend.payment.models.AcceptedPaymentsEnum;
import io.swagger.annotations.ApiModel;

@ApiModel(value="JSON representing a token which customer uses to delete a payment method.")
public class PaymentMethodDeleteDto {
    public String paymentMethodToDelete;
}
