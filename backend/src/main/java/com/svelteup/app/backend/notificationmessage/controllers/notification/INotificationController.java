package com.svelteup.app.backend.notificationmessage.controllers.notification;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.notificationmessage.dtos.NotificationDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.util.List;

public interface INotificationController {
    /**
     * @param authenticatedUser The SpringSecurity AuthenticatedUser
     * @param pageIndex the notification page to return to the user
     * @return A list of unreaduser notifications.
     * @throws OperationNotSupportedException
     */
    @GetMapping(path = ApplicationApi.GET_NOTIFICATIONS)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<NotificationDto>> getAllUnreadNotifications(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @PathVariable Integer pageIndex) throws OperationNotSupportedException, NotSupportedException;
}
