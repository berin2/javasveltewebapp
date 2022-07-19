package com.svelteup.app.backend.payment.models;

import com.braintreegateway.Customer;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToGetDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.payment.dtos.PaymentMethodStoreDto;
import com.svelteup.app.backend.payment.dtos.PaymentBaseDto;
import com.svelteup.app.backend.payment.models.paymentmethods.cards.PaymentBase;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import lombok.*;

import javax.persistence.*;

@Entity()
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class CustomerPaymentInfo extends OwningUserPrimaryKeySurrogateEntity  implements ToGetDto<PaymentMethodStoreDto> {
    protected String customerId;
    @Enumerated(EnumType.STRING)
    protected AcceptedPaymentsEnum preferredPaymentAcceptedType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentType", column = @Column(name = "visaPaymentType")),
            @AttributeOverride(name = "cardName", column = @Column(name = "visaCardName")),
            @AttributeOverride(name="paymentToken", column = @Column(name="visaPaymentToken")),
            @AttributeOverride(name="lastFour",column = @Column(name="visaLastFour"))
    })
    protected PaymentBase visaPaymentToken;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentType", column = @Column(name = "discoverPaymentType")),
            @AttributeOverride(name = "cardName", column = @Column(name = "discoverCardName")),
            @AttributeOverride(name="paymentToken", column = @Column(name="discoverPaymentToken")),
            @AttributeOverride(name="lastFour",column = @Column(name = "discoverLastFour"))
    })
    protected PaymentBase discoverPaymentToken;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentType", column = @Column(name = "amexPaymentType")),
            @AttributeOverride(name = "cardName", column = @Column(name = "amexCardName")),
            @AttributeOverride(name="paymentToken", column = @Column(name="amexPaymentToken")),
            @AttributeOverride(name="lastFour", column = @Column(name="amexLastFour"))
    })
    protected PaymentBase amexPaymentToken;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentType", column = @Column(name = "mastercardPaymentType")),
            @AttributeOverride(name = "cardName", column = @Column(name = "mastercardCardName")),
            @AttributeOverride(name="paymentToken", column = @Column(name="mastercardPaymentToken")),
            @AttributeOverride(name="lastFour",column = @Column(name="masterCardLastFour"))
    })
    protected PaymentBase masterCardPaymentToken;

    @Transient()
    protected String payPalPaymentToken;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "owningUsername",referencedColumnName = "owningUsername")
    protected SvelteUpUserProfile contactInfo;


    public CustomerPaymentInfo(String owningUser, Customer customer)
    {
        super(owningUser);
        this.visaPaymentToken = null;
        this.discoverPaymentToken = null;
        this.amexPaymentToken = null;
        this.masterCardPaymentToken = null;
        this.payPalPaymentToken = null;

        this.customerId = customer.getId();
    }


    /**
     * Returns String PaymentToken for the passed AcceptedPaymentsEnum.
     * @param tokenType the payment token matched to the AcceptedPaymentEnum
     * @return String representation of the AccecptedPaymentEnum.
     * @throws Http500Exception if the AcceptedPaymentsEnum is invalid.
     */
    public String getPaymentToken(AcceptedPaymentsEnum tokenType) throws Http500Exception
    {
        String returnToken = null;

        switch(tokenType)
        {
            case AMEX:
                returnToken = this.amexPaymentToken.getPaymentToken();
                break;
            case VISA:
                returnToken = this.visaPaymentToken.getPaymentToken();
                break;
            case PAYPAL:
                returnToken = this.payPalPaymentToken;
                break;
            case DISCOVER:
                returnToken = this.discoverPaymentToken.getPaymentToken();
                break;
            case MASTERCARD:
                returnToken = this.masterCardPaymentToken.getPaymentToken();
                break;
            default:
                throw new Http500Exception(String.format("Customer with customerId %s requested an invalid payment token typeOf %s.",customerId,this.preferredPaymentAcceptedType.name()));
        }
        return returnToken;
    }
    /**
     * Returns the Customers Preferred payment method token.
     * @return Tokenized representation of the preferred token.
     */
    public String getPreferredPaymentMethodToken()
    {
        String returnToken = null;
        switch(this.preferredPaymentAcceptedType)
        {
            case AMEX:
                returnToken = this.amexPaymentToken.getPaymentToken();
                break;
            case VISA:
                returnToken = this.visaPaymentToken.getPaymentToken();
                break;
            case PAYPAL:
                returnToken = this.payPalPaymentToken;
                break;
            case DISCOVER:
                returnToken = this.discoverPaymentToken.getPaymentToken();
                break;
            case MASTERCARD:
                returnToken = this.masterCardPaymentToken.getPaymentToken();
                break;
            default:
                throw new Http500Exception(String.format("Customer with customerId %s requested an invalid payment token typeOf %s.",customerId,this.preferredPaymentAcceptedType.name()));
        }

        if(returnToken == null || returnToken.length() == 0)
            throw new Http500Exception(String.format("Customer with customerId %s requested an preferred payment token that was null or falsy token .",customerId));


        return returnToken;
    }

    /**
     * @param paymentMethodType the paymentMethodType AMEX, DISCOVER, and Etc.
     * @param paymentMethodName the Customer assigned Name to the payment method
     * @param paymentMethodToken The actual Braintree string token produced by the gateway.
     */

    public void updatePaymentToken(String paymentMethodType, String paymentMethodName, String paymentMethodToken,Integer lastFour)
    {
        switch(paymentMethodType)
        {
            case "American Express":
                this.amexPaymentToken  = new PaymentBase(AcceptedPaymentsEnum.AMEX, paymentMethodName,paymentMethodToken,lastFour);
                break;
            case "Visa":
                this.visaPaymentToken  = new PaymentBase(AcceptedPaymentsEnum.VISA, paymentMethodName,paymentMethodToken,lastFour);
                break;
            case "PayPal":
                // not supported this.payPalPaymentToken  = paymentMethodToken;
                break;
            case "Discover":
                this.discoverPaymentToken = new PaymentBase(AcceptedPaymentsEnum.DISCOVER,paymentMethodName,paymentMethodToken,lastFour);
                break;
            case "MasterCard":
                this.masterCardPaymentToken = new PaymentBase(AcceptedPaymentsEnum.MASTERCARD,paymentMethodName,paymentMethodToken,lastFour);
                break;
            default:
                throw new Http500Exception(String.format("Customer with customerId %s requested an invalid payment token typeOf %s.",customerId,this.preferredPaymentAcceptedType.name()));
        }

        if(this.preferredPaymentAcceptedType == null)
            this.preferredPaymentAcceptedType = AcceptedPaymentsEnum.getEnumFromBrainTreePaymentString(this.owningUsername,paymentMethodType);
    }

    public void setPreferredPaymentToken(String authenticatedUser,String paymentTokenString)
    {
        AcceptedPaymentsEnum enumValue = AcceptedPaymentsEnum.getEnumFromString(authenticatedUser, paymentTokenString);
        boolean doNotThrowException = false;
        switch(enumValue)
        {
            case AMEX:
                if(doNotThrowException  = this.amexPaymentToken != null)
                    this.preferredPaymentAcceptedType = AcceptedPaymentsEnum.AMEX;
                break;
            case VISA:
                if(doNotThrowException  =this.visaPaymentToken != null)
                    this.preferredPaymentAcceptedType = AcceptedPaymentsEnum.VISA;
                break;
            case PAYPAL:
                if(doNotThrowException = this.payPalPaymentToken != null)
                    this.preferredPaymentAcceptedType = AcceptedPaymentsEnum.PAYPAL;
                break;
            case DISCOVER:
                if(doNotThrowException = this.discoverPaymentToken != null)
                    this.preferredPaymentAcceptedType = AcceptedPaymentsEnum.DISCOVER;
                break;
            case MASTERCARD:
                if(doNotThrowException = this.masterCardPaymentToken != null)
                    this.preferredPaymentAcceptedType =AcceptedPaymentsEnum.MASTERCARD;
                break;
            default:
                throw new Http500Exception(String.format("Customer with customerId %s requested an invalid payment token typeOf %s.",customerId,this.preferredPaymentAcceptedType.name()));
        }

        if(!doNotThrowException)
            throw new Http400Exception(String.format("User %s attempted to change preferred payment method to %s, but the payment token for  %s is set to null.",authenticatedUser,paymentTokenString,paymentTokenString));

    }

    public void deletePaymentToken(String paymentMethodType)
    {
        AcceptedPaymentsEnum paymentMethodToDelete = AcceptedPaymentsEnum.valueOf(paymentMethodType);

        switch(paymentMethodToDelete)
        {
            case AMEX:
                this.amexPaymentToken = null;
                break;
            case VISA:
                this.visaPaymentToken = null;
                break;
            case PAYPAL:
                this.payPalPaymentToken = null;
                break;
            case DISCOVER:
                this.discoverPaymentToken = null;
                break;
            case MASTERCARD:
                this.masterCardPaymentToken = null;
                break;
            default:
                throw new Http500Exception(String.format("Customer with customerId %s requested an invalid payment token typeOf %s.",customerId,this.preferredPaymentAcceptedType.name()));
        }

    }

    @Override
    public PaymentMethodStoreDto toGetDto()
    {
        PaymentMethodStoreDto returnDto = new PaymentMethodStoreDto();
        if(this.amexPaymentToken != null)
            returnDto.amexPaymentMethod = new PaymentBaseDto(this.amexPaymentToken);
        if(this.discoverPaymentToken != null)
            returnDto.discoverPaymentMethod = new PaymentBaseDto(this.discoverPaymentToken);
        if(this.masterCardPaymentToken != null)
            returnDto.masterCardPaymentMethod = new PaymentBaseDto(this.masterCardPaymentToken);
        if(this.visaPaymentToken != null)
            returnDto.visaPaymentMethod =  new PaymentBaseDto(visaPaymentToken);

        if(this.preferredPaymentAcceptedType != null)
            returnDto.preferredPaymentAcceptedType = this.preferredPaymentAcceptedType;

        return returnDto;
    }
}
