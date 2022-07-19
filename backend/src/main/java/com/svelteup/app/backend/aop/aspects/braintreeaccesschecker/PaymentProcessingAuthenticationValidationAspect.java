package com.svelteup.app.backend.aop.aspects.braintreeaccesschecker;

import com.braintreegateway.*;
import com.svelteup.app.backend.aop.aspects.AopBase;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


import java.util.List;
@Component()
@Aspect()
/**
 * PaymentProcessingAuthenticationValidationAspect performs variety of authentication and validation
 * checks for BrainTree financial transactions.
 */
public class PaymentProcessingAuthenticationValidationAspect extends AopBase {

    public PaymentProcessingAuthenticationValidationAspect()
    {
        super();
    }

    @AfterReturning(
            pointcut = "execution(* *.afterReturningBrainTreeTransaction*(..))",
            returning = "completedTransactionResult"
    )
    public Result<Transaction> afterReturningBrainTreeTransactionDepositResult(JoinPoint joinPoin, Result<Transaction> completedTransactionResult) throws Http400Exception {
        this.objectArgsCheck(joinPoin,2,"afterReturningBrainTreeTransactionDepositResult",this.getClass().toString());
        String requestingUser = joinPoin.getArgs()[0].toString();

        if(!completedTransactionResult.isSuccess())
            this.throwHttp400("afterReturningBrainTreeTransactionWithdrawResult","SGateWay",requestingUser,completedTransactionResult.getTransaction().getAcquirerReferenceNumber());

        return completedTransactionResult;
    }

    @AfterReturning(
            pointcut = "execution(* *.afterReturningBrainTreePaymentEntity(..))",
            returning = "completedTransactionResult"
    )
    public Result<?> afterReturningBrainTreeTransactionEntity(JoinPoint joinPoin, Result<Transaction> completedTransactionResult) throws Http400Exception {
        this.objectArgsCheck(joinPoin,2,"afterReturningBrainTreeTransactionDepositResult",this.getClass().toString());
        String requestingUser = joinPoin.getArgs()[0].toString();

        if(!completedTransactionResult.isSuccess())
            this.throwHttp400("afterReturningBrainTreeTransactionWithdrawResult","SGateWay",requestingUser,completedTransactionResult.getTransaction().getAcquirerReferenceNumber());

        return completedTransactionResult;
    }


    @AfterReturning(pointcut="execution(* *.afterReturningCreditCardEntity(..))", returning = "paymentMethodResult")
    public Result<CreditCard> afterReturningCreditCardEntityAdvice(Result<CreditCard> paymentMethodResult)
    {
        if(!paymentMethodResult.isSuccess())
        {
            List<ValidationError> validationErrorList = paymentMethodResult.getErrors().getAllValidationErrors();
            List<ValidationError> deepValidationErrorList = paymentMethodResult.getErrors().getAllDeepValidationErrors();

            if (validationErrorList != null && validationErrorList.size() > 0)
                this.throwHttp400("afterReturningCreditCardEntity", this.getClass().toString(), "SYSTEM", null, "");
            if (deepValidationErrorList != null && deepValidationErrorList.size() > 0)
                this.throwHttp400("afterReturningCreditCardEntity", this.getClass().toString(), "SYSTEM", null, "");
        }
        return paymentMethodResult;
    }

}
