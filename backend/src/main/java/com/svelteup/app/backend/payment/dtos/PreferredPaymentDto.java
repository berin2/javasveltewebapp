package com.svelteup.app.backend.payment.dtos;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.svelteup.app.backend.payment.models.AcceptedPaymentsEnum;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreferredPaymentDto is used  to transfer preferred payment data from the front end to the back end.")
public class PreferredPaymentDto {
    public AcceptedPaymentsEnum  preferredPaymentAcceptedType;
}
