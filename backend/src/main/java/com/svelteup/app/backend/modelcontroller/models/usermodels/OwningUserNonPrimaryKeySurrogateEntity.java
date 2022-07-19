package com.svelteup.app.backend.modelcontroller.models.usermodels;

import com.svelteup.app.backend.modelcontroller.models.SurrogateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Index;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@Data()
@EqualsAndHashCode(callSuper = true)
public abstract class OwningUserNonPrimaryKeySurrogateEntity extends SurrogateEntity implements Serializable {

    @Indexed()
    protected  String owningUsername;

    public OwningUserNonPrimaryKeySurrogateEntity(String owningUsername)
    {
        super();
        this.owningUsername = owningUsername;;
    }


}
