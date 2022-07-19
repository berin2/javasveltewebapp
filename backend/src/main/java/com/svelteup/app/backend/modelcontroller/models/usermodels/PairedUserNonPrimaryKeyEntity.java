package com.svelteup.app.backend.modelcontroller.models.usermodels;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data()@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor()
public abstract class PairedUserNonPrimaryKeyEntity extends OwningUserNonPrimaryKeySurrogateEntity implements Serializable {
    @Indexed()
    protected String secondaryOwningUsername;

    public PairedUserNonPrimaryKeyEntity(String owningUsername, String secondaryOwningUsername)
    {
        super(owningUsername);
        this.secondaryOwningUsername = secondaryOwningUsername;
    }

}
