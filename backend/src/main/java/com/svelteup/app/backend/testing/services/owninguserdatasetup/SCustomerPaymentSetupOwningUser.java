package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.payment.repositories.RCustomerPaymentInfo;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@NoArgsConstructor
public class SCustomerPaymentSetupOwningUser extends AOwningUserDataSetup
{   @Autowired RCustomerPaymentInfo rCustomerPaymentInfo;

    @PostConstruct
    public void constructCustomer()
    {
    }


    @Override
    public void setupEntity(OwningUserDataSetupDto owningUserDataSetupDto) {
        CustomerPaymentInfo customerPaymentInfo = new CustomerPaymentInfo(owningUserDataSetupDto.owningUser.getUsername(), SSystemEntitySetupOwningUser.TEST_SYSTEM_CUSTOMER);
        owningUserDataSetupDto.owningUserCustomerPayment = rCustomerPaymentInfo.save(customerPaymentInfo);
    }
}
