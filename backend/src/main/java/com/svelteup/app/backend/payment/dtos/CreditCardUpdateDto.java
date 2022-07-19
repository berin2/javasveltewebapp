package com.svelteup.app.backend.payment.dtos;

import com.braintreegateway.CreditCardRequest;
import com.svelteup.app.backend.payment.models.AcceptedPaymentsEnum;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import io.swagger.annotations.ApiModel;

import java.util.Locale;

@ApiModel(value = "JSON representing tokenized card information to be tokenized.")
public class CreditCardUpdateDto {
    public String firstName;
    public String lastName;
    public String addressLine;
    public String cardHolderNumber;
    public String expirationMonth;
    public String  expirationYear;
    public Integer postalCode;
    public String CVV;

    public AcceptedPaymentsEnum paymentType;
    public String cardName;
    public static final String CARD_DATE_STRING = "%s/%s";


    public String getDate()
    {
        return String.format(CARD_DATE_STRING,expirationMonth,expirationYear);
    }

    /**
     * Converts this DTO to a BrainTree CreditRequest for PaymentMethod tokenization.
     * @param paymentInfo The customer's paymentInfo.
     * @return CreditRequest the DTO converted CreditRequest.
     */
    public CreditCardRequest toCreditCardRequest(CustomerPaymentInfo paymentInfo)
    {
        CreditCardRequest createdRequest = new CreditCardRequest();
        String dateString = String.format("%s/%s", expirationMonth,expirationYear);
        createdRequest.
                customerId(paymentInfo.getCustomerId()).
                cardholderName(paymentInfo.getContactInfo().getFirstName().toUpperCase(Locale.ROOT) + " " + paymentInfo.getContactInfo().getLastName().toUpperCase(Locale.ROOT)).
                expirationDate(dateString).
                cvv(this.CVV).
                number(cardHolderNumber).
                    billingAddress().
                    postalCode(paymentInfo.getContactInfo().getUserProfileZipCode().toString()).
                    streetAddress(paymentInfo.getContactInfo().getAddressLineOne());

        return createdRequest;
    }
}
