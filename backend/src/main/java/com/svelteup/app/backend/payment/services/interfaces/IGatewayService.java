package com.svelteup.app.backend.payment.services.interfaces;

import com.braintreegateway.Request;
import com.braintreegateway.Result;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;

import javax.transaction.NotSupportedException;

/**
 * IGatewayService is an interface which contains methods used to build BrainTree Request object
 * and methods which return the result HostDescriptor from the gateway response of those BrainTree request HostDescriptor.

 */
public interface IGatewayService<ResultType,RequestTypeDto, TransactionRequest extends  Request>{
    Result<ResultType> buildGateWayTransaction(RequestTypeDto requestToBuild, CustomerPaymentInfo paymentInfo, GatewayRequestType requestType) throws NotSupportedException;
}
