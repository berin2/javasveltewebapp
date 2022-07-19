package com.svelteup.app.backend.security.services.interfaces;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http404Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;

import javax.transaction.NotSupportedException;
import java.util.UUID;

public interface IEmailVerification {
    /**
     * This endpoint  is used to verify a user email.
     * @param emailVerificationToken The UUID token sent to the email address.
     * @throws Http500Exception If the uuid token does not exist in the verification db.
     * @throws Http403Exception If the uuid token is expired. UUID tokens have a 24 hour  life span.
     */
    void get(UUID emailVerificationToken) throws Http404Exception, NotSupportedException;
    /**
     * put is used to init a new email verification token for a user.
     * @param username the authenticated user to initiate a email verification request.
     * @throws Http500Exception Throws 500 if the authenticated user does not have a currect email verification token.
     */
    void put(String username) throws Http500Exception, NotSupportedException;
}
