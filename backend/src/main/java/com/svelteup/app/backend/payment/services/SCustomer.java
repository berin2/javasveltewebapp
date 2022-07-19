package com.svelteup.app.backend.payment.services;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Result;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.services.SUserContact;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;

/**
 * SCustomer is Used to manage creation and deletion of  CustomerPayment object, as well as initially
 * assignining the customer id to the user.
 */
@Service
public class SCustomer {

    SHttpExceptionThrower exceptionThrower;
    BraintreeGateway  gateway;
    SUserContact userContactService;

    public SCustomer(BraintreeGateway  gateway, SUserContact userContact)
    {
        this.gateway = gateway;
        this.userContactService = userContact;
    }

    protected CustomerRequest createRequest(SvelteUpUserProfile contactInfo)
    {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest
                .firstName(contactInfo.getFirstName())
                .lastName(contactInfo.getLastName())
                .email(contactInfo.getEmail());

        return customerRequest;
    }

    protected Customer validateCustomerResult(String autheticatedUsername,Result<Customer> customerResult) throws Http500Exception
    {
        Customer returnCustomer = null;

        if(customerResult.isSuccess())
            returnCustomer = customerResult.getTarget();
        else
            this.exceptionThrower.throwHttp500("validateCustomerResult",this.getClass().toString(),autheticatedUsername);

        return returnCustomer;
    }

    public Customer get(String authenticatedUser) throws NotSupportedException {
        SvelteUpUserProfile contactInfo = this.userContactService.getUserContactInfo(authenticatedUser,null);
        CustomerRequest customerRequest = this.createRequest(contactInfo);
        Result<Customer> returnedCustomerResult = gateway
                .customer()
                .create(customerRequest);

        return this.validateCustomerResult(authenticatedUser,returnedCustomerResult);
    }

    public Customer post(String authenticatedUser) throws NotSupportedException {
        SvelteUpUserProfile contactInfo =  this.userContactService.getUserContactInfo(authenticatedUser,null);
        CustomerRequest customerRequest = createRequest(contactInfo);
        Result<Customer> returnedCustomerResult = gateway
                .customer()
                .create(customerRequest);

        return this.validateCustomerResult(authenticatedUser,returnedCustomerResult);
    }


    public Customer put(CustomerPaymentInfo paymentInfo) throws NotSupportedException {
        String authenticatedUser = paymentInfo.getOwningUsername();;
        SvelteUpUserProfile contactInfo =  this.userContactService.getUserContactInfo(authenticatedUser,null);
        CustomerRequest customerRequest = createRequest(contactInfo);
        Result<Customer> returnedCustomerResult = gateway
                .customer()
                .update(paymentInfo.getCustomerId(), customerRequest);

        return this.validateCustomerResult(authenticatedUser,returnedCustomerResult);
    }

    public void delete(CustomerPaymentInfo paymentInfo) throws NotSupportedException {
        String authenticatedUser = paymentInfo.getOwningUsername();;
        Result<Customer> returnedCustomerResult = gateway
                .customer()
                .delete(paymentInfo.getCustomerId());

        this.validateCustomerResult(authenticatedUser,returnedCustomerResult);
    }

    public Customer intializeEntity(String authenticatedUser, SvelteUpUserProfile profile)
    {
        CustomerRequest customerRequest = this.createRequest(profile);
        Result<Customer> returnedCustomerResult = gateway
                .customer()
                .create(customerRequest);

        return this.validateCustomerResult(authenticatedUser, returnedCustomerResult);
    }
}
