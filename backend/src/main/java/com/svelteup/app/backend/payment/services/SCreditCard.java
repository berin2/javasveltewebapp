package com.svelteup.app.backend.payment.services;

import com.braintreegateway.*;
import com.svelteup.app.backend.aop.aspects.braintreeaccesschecker.IBrainTreeAop;
import com.svelteup.app.backend.aop.aspects.braintreeaccesschecker.SBrainTreeAop;
import com.svelteup.app.backend.aop.aspects.owninguserpk.OwningUserPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserPk;
import com.svelteup.app.backend.payment.dtos.CreditCardUpdateDto;
import com.svelteup.app.backend.payment.dtos.PaymentMethodStoreDto;
import com.svelteup.app.backend.payment.dtos.PreferredPaymentDto;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.payment.repositories.RCustomerPaymentInfo;
import com.svelteup.app.backend.payment.services.interfaces.GatewayRequestType;
import com.svelteup.app.backend.payment.services.interfaces.GatewayUsernameService;
import com.svelteup.app.backend.payment.services.interfaces.IGatewayService;
import com.svelteup.app.backend.profile.services.SUserContact;
import com.svelteup.app.backend.profile.services.SUserProfile;
import com.svelteup.app.backend.profile.services.SUserProfileAddress;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

/**
 * SCreditCard is used for creating CreditCardTokens for users of the application.
 */
@Service()
public class SCreditCard extends SSurrogateEntityOwningUserPk<CustomerPaymentInfo>
        implements GatewayUsernameService<PaymentMethodStoreDto,CreditCardUpdateDto,CreditCardUpdateDto, CreditCardUpdateDto>,
                   IGatewayService<CreditCard,CreditCardUpdateDto,CreditCardRequest>
{

    protected final RCustomerPaymentInfo customerPaymentRepository;
    protected final SUserProfileAddress userProfileAddress;
    protected final SUserProfile userProfile;
    protected final SCustomer customerService;
    protected final SUserContact userContactService;
    protected final CreditCardGateway braintreeCreditCardGateway;
    protected final SBrainTreeAop sBrainTreeAop;

    public SCreditCard(RCustomerPaymentInfo customerPaymentRepository, SUserProfileAddress userProfileAddress, SUserProfile userProfile, SCustomer customerService, SUserContact userContactService, CreditCardGateway braintreeCreditCardGateway,SBrainTreeAop sBrainTreeAop) {
        super(customerPaymentRepository);
        this.customerPaymentRepository = customerPaymentRepository;
        this.userProfileAddress = userProfileAddress;
        this.userProfile = userProfile;
        this.customerService = customerService;
        this.userContactService = userContactService;
        this.braintreeCreditCardGateway = braintreeCreditCardGateway;
        this.sBrainTreeAop = sBrainTreeAop;
    }


    @Override
    public void post(String authenticatedUser, CreditCardUpdateDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {


        CustomerPaymentInfo authUserPaymentInfo = this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
        CreditCardRequest creditCardRequest =  create_DTO.toCreditCardRequest(authUserPaymentInfo);
        Result<CreditCard> creditCardResult = this.braintreeCreditCardGateway.create(creditCardRequest);

        creditCardResult = this.sBrainTreeAop.afterReturningCreditCardEntity(creditCardResult);
        CreditCard resultingCard = creditCardResult.getTarget();
        authUserPaymentInfo.updatePaymentToken(resultingCard.getCardType(),create_DTO.cardName, resultingCard.getToken(),Integer.valueOf(resultingCard.getLast4()));
        this.customerPaymentRepository.save(authUserPaymentInfo);
    }

    @Override
    public ResponseEntity<PaymentMethodStoreDto> get(String username) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        CustomerPaymentInfo discoveredPaymentInfo = this.findBySurrogateIdWithCheck(username,username);
        return ResponseEntity.ok(discoveredPaymentInfo.toGetDto());
    }

    @Override
    public void put(String authenticated_user, CreditCardUpdateDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        CustomerPaymentInfo discoveredPaymentInfo = this.findBySurrogateIdWithCheck(authenticated_user,authenticated_user);
        Result<CreditCard> returnedResult = this.buildGateWayTransaction(update_DTO,discoveredPaymentInfo,GatewayRequestType.PUT);
        returnedResult  = this.sBrainTreeAop.afterReturningCreditCardEntity(returnedResult);
        CreditCard updatedCard = returnedResult.getTarget();
        discoveredPaymentInfo.updatePaymentToken(updatedCard.getCardType(), update_DTO.cardName,updatedCard.getToken(),this.extractLastFour(update_DTO.cardHolderNumber));
    }

    public void putPrefPayment(String authenticatedUser, PreferredPaymentDto preferredPaymentDto) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException
    {
        CustomerPaymentInfo customerPaymentInfo = this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
        customerPaymentInfo.setPreferredPaymentToken(authenticatedUser,preferredPaymentDto.preferredPaymentAcceptedType.toString());
        this.customerPaymentRepository.save(customerPaymentInfo);
    }

    @Override
    public void delete(String username,CreditCardUpdateDto deleteDto) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException {
        CustomerPaymentInfo discoveredPaymentInfo = this.findBySurrogateIdWithCheck(username,username);
        Result<CreditCard> creditCardResult = this.buildGateWayTransaction(deleteDto,discoveredPaymentInfo,GatewayRequestType.DELETE);
        this.sBrainTreeAop.afterReturningCreditCardEntity(creditCardResult);
        discoveredPaymentInfo.deletePaymentToken(deleteDto.paymentType.name());
        this.customerPaymentRepository.save(discoveredPaymentInfo);
    }



    @Override
    public Result<CreditCard> buildGateWayTransaction(CreditCardUpdateDto requestToBuild, CustomerPaymentInfo paymentInfo, GatewayRequestType requestType) throws NotSupportedException {
        CreditCardRequest request = null;
        Result<CreditCard> creditCardRequestTransaction = null;
        String token = paymentInfo.getPaymentToken(requestToBuild.paymentType);

        switch(requestType)
        {
            case POST:
                request = requestToBuild.toCreditCardRequest(paymentInfo);
                creditCardRequestTransaction = this.braintreeCreditCardGateway.create(request);
                break;
            case PUT:
                request = requestToBuild.toCreditCardRequest(paymentInfo);
                creditCardRequestTransaction = this.braintreeCreditCardGateway.update(token,request);
                break;
            case DELETE:
                creditCardRequestTransaction = this.braintreeCreditCardGateway.delete(token);
                break;
        }

        return creditCardRequestTransaction;
    }

    protected Integer extractLastFour(String number) {
        String cardNumberString = number.toString();
        cardNumberString = cardNumberString.substring(cardNumberString.length() - 4 , cardNumberString.length());
        return Integer.valueOf(cardNumberString);
    }

}
