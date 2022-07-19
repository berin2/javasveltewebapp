package com.svelteup.app.backend.notificationmessage.models;

import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import com.svelteup.app.backend.notificationmessage.events.NotificationEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data() @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor()
public class Notification extends PairedUserNonPrimaryKeyEntity {
    @Id
    @GeneratedValue
    protected Long notificationId;
    protected String notificationSubject;
    protected String notificationNoun;
    protected Timestamp notificationDate;
    protected Boolean notificationRead;
    protected Integer notificationTypeEnumOrdinalValue; //Represents the enum values af the NotificationEnums.

    public Notification(String senderRecieverUser, String owningUser, String  noun,Integer notifcationOrdinal)
    {
        super(senderRecieverUser,owningUser);
        this.notificationNoun = noun;
        this.notificationDate = new Timestamp(System.currentTimeMillis());
        this.notificationRead = false;
        this.notificationTypeEnumOrdinalValue = notifcationOrdinal;
    }

    public Notification(NotificationEvent notificationChangeEvent) {
        super(notificationChangeEvent.getNotificationSender(),notificationChangeEvent.getNotificationOwner());
        this.notificationNoun = notificationChangeEvent.getNotificationNoun();
        this.notificationSubject = notificationChangeEvent.getNotificationSubject();
        this.notificationDate = new Timestamp(System.currentTimeMillis());
        this.notificationRead = false;
        this.notificationTypeEnumOrdinalValue = notificationChangeEvent.getUpdatedOrderStatus().ordinal();
    }

}
