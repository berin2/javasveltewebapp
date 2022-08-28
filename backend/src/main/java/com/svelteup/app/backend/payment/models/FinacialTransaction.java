package com.svelteup.app.backend.payment.models;

import com.svelteup.app.backend.modelcontroller.models.SurrogateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity()
@Data()
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor()
public class FinacialTransaction extends SurrogateEntity {

    public FinacialTransaction(double cost, String transactionDescription)
    {
        super();
        this.cost = cost;
        this.timeStamp = System.currentTimeMillis();
    }
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(nullable = false)
    Double cost;
    @Column(nullable = false)
    Long timeStamp;
    @Column(nullable = false)
    String description;

}
