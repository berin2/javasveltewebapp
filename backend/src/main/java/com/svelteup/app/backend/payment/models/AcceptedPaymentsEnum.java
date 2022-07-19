package com.svelteup.app.backend.payment.models;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.payment.models.paymentmethods.cards.PaymentBase;

/**
 * AcceptedPaymentsEnum is a JPA complaint enum used to describe
 * the set of possible accepted payment types.
 */
public enum AcceptedPaymentsEnum {
    VISA,
    MASTERCARD,
    DISCOVER,
    AMEX,
    PAYPAL,
    NOTADDED;

    public static AcceptedPaymentsEnum getEnumFromString(String username,String enumValue) throws Http400Exception
    {
        AcceptedPaymentsEnum retVal = null;
        Exception caughtException = null;

        try{ retVal =  AcceptedPaymentsEnum.valueOf(enumValue);}
        catch (Exception e) { caughtException = e; }

        if(caughtException != null)
        {
            username =  username == null || username.length()== 0 ? "UNKNOWN_USER" : username;
            throw new Http400Exception("Attempted to get an AcceptedPaymentsEnum with an incompatible or unknown enum string value. Requested by user " + username + ".");
        }

        return retVal;
    }

    public static AcceptedPaymentsEnum getEnumFromBrainTreePaymentString(String username,String braintreePaymentType) throws Http400Exception
    {
        AcceptedPaymentsEnum retVal = null;
        Exception caughtException = null;

        try{
            switch(braintreePaymentType)
            {
                case "American Express":
                    retVal = AMEX;
                    break;
                case "Visa":
                    retVal = VISA;
                    break;
                case "PayPal":
                    // not supported this.payPalPaymentToken  = paymentMethodToken;
                    retVal = NOTADDED;
                    break;
                case "Discover":
                    retVal = DISCOVER;
                    break;
                case "MasterCard":
                    retVal= MASTERCARD;
                    break;
                default:
                    throw new Http500Exception(String.format("Invalid braintree payment type with string %s was passed but recognized by AcceptedPaymentsEnum string to AcceptedPaymentsEnum conversion."));
            }
        }
        catch (Exception e) { caughtException = e; }

        if(caughtException != null)
        {
            username =  username == null || username.length()== 0 ? "UNKNOWN_USER" : username;
            throw new Http400Exception("Attempted to get an AcceptedPaymentsEnum with an incompatible or unknown enum string value. Requested by user " + username + ".");
        }

        return retVal;
    }
}
