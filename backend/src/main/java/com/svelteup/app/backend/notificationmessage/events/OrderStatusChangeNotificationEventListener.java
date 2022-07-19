package com.svelteup.app.backend.notificationmessage.events;

import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class OrderStatusChangeNotificationEventListener implements ApplicationListener<NotificationEvent> {

    private SOrderStatusNotification notificationService;

    @Override
    public void onApplicationEvent(NotificationEvent orderStatusChangeNotificationEvent) {

        ApplicationNotificationEnums eventChange = orderStatusChangeNotificationEvent.getUpdatedOrderStatus();
        switch (eventChange) {
            case ORDER_PLACED: this.notificationService.postNotificationEvent(orderStatusChangeNotificationEvent); break;
            case ORDER_ACCEPTED: break;
            case ORDER_CANCEL_REQUESTED: break;
            case ORDER_CANCEL_ACCEPTED: break;
        }
    }
}
