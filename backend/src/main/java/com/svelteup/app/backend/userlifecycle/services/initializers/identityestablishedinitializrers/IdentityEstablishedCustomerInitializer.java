package com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers;

import com.braintreegateway.Customer;
import com.svelteup.app.backend.payment.services.SCustomer;
import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.EntityInitializer;
import org.springframework.stereotype.Component;

@Component
public class IdentityEstablishedCustomerInitializer extends EntityInitializer<EstablishCustomerIdentityDto> {
    protected final SCustomer customerService;

    public IdentityEstablishedCustomerInitializer(SCustomer customerService) {
        super(false);
        this.customerService = customerService;
    }

    @Override
    public void initialize(EstablishCustomerIdentityDto customerIdentityDto)
    {
        Customer createdCustomer =this.customerService.intializeEntity(customerIdentityDto.user.getUsername(),customerIdentityDto.userProfile);
        customerIdentityDto.userCustomer = createdCustomer;
    }
}
