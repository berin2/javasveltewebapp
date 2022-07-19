package com.svelteup.app.backend.notificationmessage.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.notificationmessage.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RNotification extends RSurrogateJpaRepository<Notification,Long> {
    Page<Notification> getNotificationsByNotificationReadIsFalseAndOwningUsername(String owningUsername, Pageable pageToFetch);
    Page<Notification> getNotificationByNotificationReadIsTrueAndOwningUsername(String owningUsername, Pageable pageToFetch);
    List<Notification> findNotificationByOwningUsernameAndNotificationReadIsFalse(String username);
}
