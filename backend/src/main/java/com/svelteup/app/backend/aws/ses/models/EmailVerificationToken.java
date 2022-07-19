package com.svelteup.app.backend.aws.ses.models;

import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class EmailVerificationToken extends OwningUserPrimaryKeySurrogateEntity {
    protected long emailVerificationTokenExpiry;
    protected String emailVerificationTokenEmail;
    protected UUID emailToken;

    public EmailVerificationToken(String username,String userEmail)
    {
        super(username);
        this.refreshToken();
        this.emailVerificationTokenEmail = userEmail;
    }

    public void refreshToken(String email)
    {
        this.emailVerificationTokenEmail   = email;
        this.emailToken  = UUID.randomUUID();
        this.emailVerificationTokenExpiry  =  System.currentTimeMillis() + 86400000L;
    }

    public void refreshToken()
    {
        this.emailToken  = UUID.randomUUID();
        this.emailVerificationTokenExpiry  =  System.currentTimeMillis() + 86400000L;
    }
}
