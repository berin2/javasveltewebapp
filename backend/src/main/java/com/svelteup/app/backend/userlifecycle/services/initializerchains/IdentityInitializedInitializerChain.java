package com.svelteup.app.backend.userlifecycle.services.initializerchains;

import com.svelteup.app.backend.userlifecycle.dtos.EstablishCustomerIdentityDto;
import com.svelteup.app.backend.userlifecycle.services.initializers.identityestablishedinitializrers.*;
import org.springframework.stereotype.Component;

@Component
public class IdentityInitializedInitializerChain extends EntityInitializerChain<EstablishCustomerIdentityDto> {

    protected static final Integer IDENTITY_ESTABLISHED_INITIALIZE_INDEX = 0;
    protected static final Integer SVELTE_UP_USER_PROFILE_INITIALIZE_INDEX = 1;
    protected static final Integer CUSTOMER_INITIALIZE_INDEX = 2;
    protected static final Integer CUSTOMER_PAYMENT_INITIALIZE_INDEX = 3;
    protected static final Integer ESTABLISHED_AUTHORITY_INITIALIZER = 4;
    protected static final Integer REFRESH_USER_ACCOUNT_LOGIN_INITIALIZER = 5;
    protected static final Integer GRANT_FULL_AUTHORITY_INITIALIZER = 6;

    protected final static Integer LIST_SIZE = 100;

    public IdentityInitializedInitializerChain(
            IdentityEstablishedSvelteUpProfileIdentityInitializer profileIdentityInitializer,
            IdentityEstablishedCustomerInitializer customerInitializer,
            IdentityEstablishedCustomerPaymentInitializer paymentInitializer,
            IdentityEstablishedCustomerPaymentInitializer customerPaymentInitializer,
            IdentityEstablishedAuthorityInitializer authorityInitializer,
            IdentityEstablishedRefreshUserStoreInitializer refreshUserStoreInitializer,
            IdentityEstablishedGrantFullAuthorityInitializer fullAuthorityInitializer
    )
    {
        super(LIST_SIZE);
        this.addToInitializeChain(IDENTITY_ESTABLISHED_INITIALIZE_INDEX,profileIdentityInitializer);
        this.addToInitializeChain(SVELTE_UP_USER_PROFILE_INITIALIZE_INDEX,customerInitializer);
        this.addToInitializeChain(CUSTOMER_INITIALIZE_INDEX,paymentInitializer);
        this.addToInitializeChain(CUSTOMER_PAYMENT_INITIALIZE_INDEX, customerPaymentInitializer);
        this.addToInitializeChain(ESTABLISHED_AUTHORITY_INITIALIZER,authorityInitializer);
        this.addToInitializeChain(REFRESH_USER_ACCOUNT_LOGIN_INITIALIZER, refreshUserStoreInitializer);
        this.addToInitializeChain(GRANT_FULL_AUTHORITY_INITIALIZER,fullAuthorityInitializer);
    }
}
