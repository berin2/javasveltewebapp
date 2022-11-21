package com.svelteup.app.backend.userlifecycle.dtos;

import com.braintreegateway.Customer;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;


public class EstablishCustomerIdentityDto {
    public SvelteUpUserProfileDto svelteUpUserProfileDto;
    public SvelteUpUser user;
    public SvelteUpUserProfile userProfile;
    public Customer userCustomer;
    public CustomerPaymentInfo paymentInfo;
}
