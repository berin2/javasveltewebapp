package com.svelteup.app.backend.userlifecycle.dtos;

import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import com.svelteup.app.backend.security.models.SvelteUpUser;

import java.util.UUID;

public class EmailVerifyInitializerDto {
    public UUID emailToken;
    public EmailVerificationToken tokenEntity;
    public SvelteUpUser securityContextUser;
}
