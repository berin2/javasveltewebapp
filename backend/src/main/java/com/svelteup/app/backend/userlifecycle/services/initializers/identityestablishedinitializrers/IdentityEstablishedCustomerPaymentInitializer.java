package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.svelteup.app.backend.payment.services.SCustomerPaymentInfo;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.stereotype.Component;

@Component
public class IdentityEstablishedCustomerPaymentInitializer extends EntityInitializer<EstablishCustomerIdentityDto>  {
    protected final SCustomerPaymentInfo customerPaymentInfo;
    public IdentityEstablishedCustomerPaymentInitializer(SCustomerPaymentInfo customerPaymentInfo) {
        super(false);
        this.customerPaymentInfo = customerPaymentInfo;
    }

    @Override
    public void initialize(EstablishCustomerIdentityDto dto)
    {
        customerPaymentInfo.initializeEntity(dto.userProfile.owningUsername);
    }
}
