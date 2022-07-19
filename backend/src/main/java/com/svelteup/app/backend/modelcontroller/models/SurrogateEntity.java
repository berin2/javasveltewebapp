package com.svelteup.app.backend.modelcontroller.models;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public class SurrogateEntity implements Serializable {
    @NaturalId
    @Column(updatable = false,insertable = true, unique = true)
    protected UUID surrogateId;

    protected SurrogateEntity(){
        this.surrogateId = UUID.randomUUID();
    }
}
