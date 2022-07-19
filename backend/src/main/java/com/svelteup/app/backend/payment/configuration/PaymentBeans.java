package com.svelteup.app.backend.payment.configuration;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCardGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
public class PaymentBeans {

    @Bean()
    @Profile("dev")
    public BraintreeGateway getBrainTreeGateWay(Environment applicationContextEnvironment)
    {
        BraintreeGateway paymentGateway;
        String merchantId = applicationContextEnvironment.getProperty("merchant_id");
        String publicKey = applicationContextEnvironment.getProperty("public_key");
        String privateKey = applicationContextEnvironment.getProperty("private_key");
        paymentGateway = new BraintreeGateway(com.braintreegateway.Environment.SANDBOX,merchantId,publicKey,privateKey);
        return paymentGateway;
    }

    @Bean()
    public CreditCardGateway getCreditCardGateway(BraintreeGateway braintreeGateway)
    {
        return braintreeGateway.creditCard();
    }
}
