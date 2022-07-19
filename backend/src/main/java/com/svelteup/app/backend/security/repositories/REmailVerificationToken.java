package com.svelteup.app.backend.security.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.aws.ses.models.EmailVerificationToken;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * REmailVerificationToken is responsible for managing EmailVerificationTokens.
 */
@Repository
public interface REmailVerificationToken extends RSurrogateJpaRepository<EmailVerificationToken,String> {
    EmailVerificationToken findByEmailToken(UUID emailToken);
}
