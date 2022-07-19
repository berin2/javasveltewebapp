package com.svelteup.app.backend.payment.services;

import com.braintreegateway.*;
import com.svelteup.app.backend.aop.aspects.braintreeaccesschecker.SBrainTreeAop;
import com.svelteup.app.backend.aop.aspects.owninguserpk.OwningUserPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserPk;
import com.svelteup.app.backend.payment.dtos.ClientTokenDto;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;

import com.svelteup.app.backend.productorder.events.TransferMoneyEvent;
import lombok.EqualsAndHashCode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.math.BigDecimal;
import java.util.UUID;

@Service()
@EqualsAndHashCode(callSuper = true)
public class SGateWayFinancialProcessing extends SSurrogateEntityOwningUserPk<CustomerPaymentInfo>  {

    protected final BraintreeGateway paymentGateway;
    protected final SBrainTreeAop sBrainTreeAop;


    public SGateWayFinancialProcessing(BraintreeGateway gateway, SBrainTreeAop sBrainTreeAop,RSurrogateJpaRepository<CustomerPaymentInfo, String> surrogateJpaRepository, Environment applicationContextEnvironment) {
        super(surrogateJpaRepository);
        this.paymentGateway = gateway;
        this.sBrainTreeAop = sBrainTreeAop;
    }


    /**
     * generateClientToken generateClientToken is used to generate a client token for the use of
     * the front end. The clientToken is returned to the  frontend in which casethe frontend can then use it to obtain
     * a nOnce for server side payment processing.
     * @param authenticatedUser The user who wishes to obtain a nonce.
     * @param customerPaymentInfo The CustomerPaymentInfo linked to the customer to validate.
     * @return ClientTokenDto containing the username and client token to be sent to the front end.
     */
    public ClientTokenDto generateClientToken(String authenticatedUser,CustomerPaymentInfo customerPaymentInfo)
    {
        String customerId = customerPaymentInfo.getCustomerId();
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        clientTokenRequest.customerId(customerId);
        String brainTreeClientToken =  this.paymentGateway
                .clientToken()
                .generate(clientTokenRequest);

        return new ClientTokenDto(authenticatedUser,brainTreeClientToken);
    }


    public TransactionRequest buildTransactionRequest(BigDecimal orderCost, String buyerPaymentNonceToken, String deviceData)
    {
        TransactionRequest buyerMoneyWithdrawalRequest = new TransactionRequest()
                .amount(orderCost)
                .paymentMethodNonce(buyerPaymentNonceToken)
                .deviceData(deviceData)
                .options()
                .submitForSettlement(true)
                .done();

        return  buyerMoneyWithdrawalRequest;
    }

    public void transferMoney(TransferMoneyEvent transferMoneyEvent) throws NotSupportedException {

       String fromUser = transferMoneyEvent.getFromUser();
       String toUser = transferMoneyEvent.getToUser();
       Double transactionAmount = transferMoneyEvent.getAmount();;

       CustomerPaymentInfo fromInfo = this.findBySurrogateIdWithCheck(fromUser,fromUser);
       CustomerPaymentInfo toInfo = this.findBySurrogateIdWithCheck(toUser,toUser);

       BigDecimal orderCost = BigDecimal.valueOf(transactionAmount);

       String fromPaymentNonceToken = this.beforeBuildUserNonceToken(fromUser,fromInfo);
       TransactionRequest buyerMoneyWithdrawalRequest = this.buildTransactionRequest(orderCost,fromPaymentNonceToken,"DEVICE DATA PLACEHOLDER");
       Result<Transaction> result = this.paymentGateway
               .transaction()
               .sale(buyerMoneyWithdrawalRequest); // sale transfers money from user to buy into our account.

       this.sBrainTreeAop.afterReturningBrainTreeTransactionWithdrawResult(fromUser,result);


       String sellerPaymentNonceToken = this.beforeBuildUserNonceToken(toUser,toInfo);
       TransactionRequest sellerMoneyDepositRequest = this.buildTransactionRequest(orderCost,sellerPaymentNonceToken,"DEVICE DATA PLACEHOLDER");
       Result<Transaction> sellerCreditResult = paymentGateway.
               transaction()
               .credit(sellerMoneyDepositRequest); //credit our account for the transaction amount.

       this.sBrainTreeAop.afterReturningBrainTreeTransactionDepositResult(toUser,sellerCreditResult);
    }

    public String beforeBuildUserNonceToken(String nOnceRequestingUser, CustomerPaymentInfo customerPaymentInfo) throws Http400Exception
    {
        String paymentMethodToken = customerPaymentInfo.getPreferredPaymentMethodToken();
        String returnNonceToken = null;
        Result<PaymentMethodNonce> requestedNonceResult = this.paymentGateway
                .paymentMethodNonce()
                .create(paymentMethodToken);

        this.sBrainTreeAop.beforeBuildUserNonceToken(nOnceRequestingUser,customerPaymentInfo);

        if(requestedNonceResult.isSuccess())
            returnNonceToken = requestedNonceResult.getMessage();
        else
            this.throwHttp400("getPaymentNonce",this.getClass().toString(),nOnceRequestingUser,"PaymentMethodToken: "+ paymentMethodToken);

        return returnNonceToken;
    }

}
