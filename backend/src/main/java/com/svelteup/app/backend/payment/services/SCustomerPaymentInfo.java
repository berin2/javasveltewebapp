package com.svelteup.app.backend.payment.services;

import com.braintreegateway.Customer;
import com.svelteup.app.backend.aop.aspects.owninguserpk.OwningUserPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.aws.ses.events.VerifyEvent;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.NonHttpService;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserPk;
import com.svelteup.app.backend.payment.dtos.CreditCardUpdateDto;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.payment.repositories.RCustomerPaymentInfo;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.profile.services.SUserContact;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
public class SCustomerPaymentInfo extends SSurrogateEntityOwningUserPk<CustomerPaymentInfo>
        implements NonHttpService<CustomerPaymentInfo>,
                   ApplicationListener<VerifyEvent>
{
    protected RCustomerPaymentInfo paymentInfo;
    protected SUserContact userContactService;
    protected SCustomer customerService;
    protected SCreditCard sCreditCard;

    public SCustomerPaymentInfo(SCustomer customerService, SUserContact userContactService, RCustomerPaymentInfo surrogateJpaRepository) {
        super(surrogateJpaRepository);
        this.paymentInfo = surrogateJpaRepository;
        this.userContactService = userContactService;
        this.customerService = customerService;
    }


    @Override
    public CustomerPaymentInfo get(String authenticatedUser, UUID surrogateid) throws NotSupportedException {
        return this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
    }

    @Override
    public CustomerPaymentInfo post(SvelteUpUser user) throws NotSupportedException {

        String username = user.getUsername();
        Customer userCustomer = this.customerService.get(username);
        CustomerPaymentInfo newCustomer = new CustomerPaymentInfo(user.getUsername(),userCustomer);
        return this.paymentInfo.save(newCustomer);
    }

    public void put(String authenticated, CreditCardUpdateDto updateDto) throws NotSupportedException {
            sCreditCard.post(authenticated, updateDto);
    }

    @Override
    public void delete(String authenticatedUser, UUID surrogateId) throws NotSupportedException {
        CustomerPaymentInfo discoveredInfo = this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
        this.customerService.delete(discoveredInfo);
    }


    @Override
    public void onApplicationEvent(VerifyEvent event) {

    }

    public void initializeEntity(String authenticatedUser)
    {
        //customerService.intializeEntity()
       // CustomerPaymentInfo newCustomer = new CustomerPaymentInfo(authenticatedUser,customerProfile);
        //this.paymentInfo.save(newCustomer);
    }

}
