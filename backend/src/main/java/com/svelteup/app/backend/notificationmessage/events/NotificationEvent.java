package com.svelteup.app.backend.notificationmessage.events;

import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/*
* NotificationEvent is used to broadcast events related to Notifying
* users of order status changes.
* */
public class NotificationEvent extends ApplicationEvent{
    @Getter private String notificationOwner;
    @Getter private String notificationSender;
    @Getter private String notificationSubject;
    @Getter private String notificationNoun;
    @Getter private ApplicationNotificationEnums updatedOrderStatus;


    public NotificationEvent(ProductOrder sourceEventOrder, String notificationOwner, String notificationSubject, String notificationNoun, ApplicationNotificationEnums productOrderStatusUpdate) {
        super(sourceEventOrder);
        this.notificationOwner = notificationOwner;
        this.notificationSender = sourceEventOrder.getOwningUsername();
        this.notificationSubject =  notificationSubject;
        this.notificationNoun = notificationNoun;
        this.updatedOrderStatus = productOrderStatusUpdate;
    }
}
