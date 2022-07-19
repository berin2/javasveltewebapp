package com.svelteup.app.backend.payment.controller;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.payment.dtos.CreditCardUpdateDto;
import com.svelteup.app.backend.payment.dtos.PaymentMethodStoreDto;
import com.svelteup.app.backend.payment.dtos.PreferredPaymentDto;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.payment.services.SCreditCard;
import com.svelteup.app.backend.payment.services.SCustomerPaymentInfo;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.NotSupportedException;

@Controller
@AllArgsConstructor
public class IClientPaymentControllerImpl implements IClientPaymentController {
    protected  SCustomerPaymentInfo customerPaymentInfo;
    protected  SCreditCard sCreditCard;

    @Override
    public void post(SvelteUpUser authenticatedUser, CreditCardUpdateDto creditCardUpdateDto) throws Http405Exception, NotSupportedException {

    }

    @Override
    public void put(SvelteUpUser authenticatedUser, CreditCardUpdateDto creditCardUpdateDto) throws Http400Exception, NotSupportedException {
        sCreditCard.post(authenticatedUser.getUsername(),creditCardUpdateDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, CreditCardUpdateDto deleteDto) throws Http405Exception, NotSupportedException {
        sCreditCard.delete(authenticatedUser.getUsername(),deleteDto);
    }

    @Override
    public ResponseEntity<PaymentMethodStoreDto> get(SvelteUpUser authenticatedUser) throws NotSupportedException {
        CustomerPaymentInfo paymentInfo = customerPaymentInfo.findBySurrogateIdWithCheck(authenticatedUser.getUsername(),authenticatedUser.getUsername());
        return ResponseEntity.ok(paymentInfo.toGetDto());
    }

    @PutMapping(ApplicationApi.CLIENT_PREF_PAYMENT)
    public void putPrefPayment(@AuthenticationPrincipal SvelteUpUser svelteUpUser, @RequestBody PreferredPaymentDto  preferredPaymentDto) throws NotSupportedException {
        this.sCreditCard.putPrefPayment(svelteUpUser.getUsername(),preferredPaymentDto);
    }
}
