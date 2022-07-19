package com.svelteup.app.backend.payment.services.interfaces;

/**
 * GatewayRequestType represents the type the customer would like to make.
 * Currently suports PUT,POST, and DELETE operations.
 */
public enum GatewayRequestType {
    PUT,
    POST,
    DELETE
}
