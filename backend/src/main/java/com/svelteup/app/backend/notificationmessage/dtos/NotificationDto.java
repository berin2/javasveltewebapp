package com.svelteup.app.backend.notificationmessage.dtos;

import com.svelteup.app.backend.notificationmessage.models.Notification;
import com.svelteup.app.backend.utils.dateutils.StaticDateService;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "NotificationDto represents JSON dto used to transfr notification data. ")
public class NotificationDto {

    public String sender;
    public String notificationDate;
    public Boolean read;
    public String notificationMessage;

    public NotificationDto(Notification notification, String  notificationMessage)
    {
        this.sender = notification.getSecondaryOwningUsername();
        this.notificationMessage = notificationMessage;
        this.read = notification.getNotificationRead();
        this.notificationDate = StaticDateService.getDateString(notification.getNotificationDate());
    }
}
