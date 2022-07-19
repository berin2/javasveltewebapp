package com.svelteup.app.backend.aop.aspects.braintreeaccesschecker;


import com.braintreegateway.*;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;

/**
 *
 */
public interface IBrainTreeAop {
    /**
     *
     * @param paymentMethodResult the result returned by the gateway.
     * @return Entity extending PaymentMethod when the  underlying result is succesful.
     * @throws Http400Exception when the PaymentMethod information was not found. For example, a 400 will be thrown
     * when a user's submitted credit card is not found.
     */
     Result<CreditCard> afterReturningCreditCardEntity(Result<CreditCard> paymentMethodResult) throws Http400Exception;
    /**
     * ensures a Transaction is a success, and if not throws Http400Exception.
     * @param requestingUser the system registered user the transaction is requested on the behalf of.
     * @param transactionResult the transactionResult returned by the gateway processing a TransactionRequest
     * @return TransactionResult if the transaction was succesful
     * @throws Http400Exception if the transaction failed.
     */
    public Result<Transaction> afterReturningBrainTreeTransactionWithdrawResult(String requestingUser, Result<Transaction> transactionResult) throws Http400Exception;

    /**
     * ensures a Transaction is a success, and if not throws Http400Exception.
     * @param requestingUser the system registered user the transaction is requested on the behalf of.
     * @param transactionResult the transactionResult returned by the gateway processing a TransactionRequest
     * @param requestingUser the auithenticated user who wishes to create the transaction.
     * @return TransactionResult if the transaction was succesful
     * @throws Http400Exception if the transaction failed.
     */
    public Result<Transaction> afterReturningBrainTreeTransactionDepositResult(String requestingUser,Result<Transaction> transactionResult) throws Http400Exception;

    /**
     * ensures a user owns a CustomerPaymentInfo before requesting the gateway to generate a nonce.
     * @param transactionUser the user who wishes to create a payment nonce
     * @param transactionUserInfo the CustomerPaymentInfo for which the user wishes to use create a paymentNonce.
     * @throws Http400Exception if the transaction failed.
     */
    public CustomerPaymentInfo beforeBuildUserNonceToken(String transactionUser, CustomerPaymentInfo transactionUserInfo) throws Http403Exception;
}
