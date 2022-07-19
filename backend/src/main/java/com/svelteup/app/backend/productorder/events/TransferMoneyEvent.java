package com.svelteup.app.backend.productorder.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
public class TransferMoneyEvent extends ApplicationEvent {

    @Getter protected Double amount;
    @Getter protected String fromUser;
    @Getter protected String toUser;

    public TransferMoneyEvent(Object source) {
        super(source);
    }

    public TransferMoneyEvent(Object source, Double amount, String fromUser, String toUser)
    {
        super(source);
        this.amount = amount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

}
