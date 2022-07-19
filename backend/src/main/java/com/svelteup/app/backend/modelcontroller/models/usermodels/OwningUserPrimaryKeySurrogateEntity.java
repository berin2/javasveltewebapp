package com.svelteup.app.backend.modelcontroller.models.usermodels;

import com.svelteup.app.backend.modelcontroller.models.SurrogateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data() @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class OwningUserPrimaryKeySurrogateEntity extends SurrogateEntity implements Serializable {
    @Id
    @Column(name="owningUsername", updatable = false,insertable = true, nullable = false)
    public String owningUsername;

    public OwningUserPrimaryKeySurrogateEntity(String owningUser)
    {
        super();
        this.owningUsername = owningUser;
    }
}
