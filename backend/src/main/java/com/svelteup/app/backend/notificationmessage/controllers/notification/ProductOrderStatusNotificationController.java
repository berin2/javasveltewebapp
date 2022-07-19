package com.svelteup.app.backend.notificationmessage.controllers.notification;

import com.svelteup.app.backend.notificationmessage.dtos.NotificationDto;
import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductOrderStatusNotificationController implements INotificationController{

    private SOrderStatusNotification orderStatusNotificationService;

    @Override
    public ResponseEntity<List<NotificationDto>> getAllUnreadNotifications(SvelteUpUser authenticatedUser, Integer pageIndex) throws OperationNotSupportedException, NotSupportedException {

        List<NotificationDto> unreadNotificationList = orderStatusNotificationService.getNotificationPageForUsername(authenticatedUser.getUsername(),pageIndex);
        return ResponseEntity.ok(unreadNotificationList);
    }
}
