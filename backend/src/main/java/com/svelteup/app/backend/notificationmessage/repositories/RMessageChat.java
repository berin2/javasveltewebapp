package com.svelteup.app.backend.notificationmessage.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RMessageChat extends RSurrogateJpaRepository<MessageChat,Long> {
    /**
     * Returns all MessageChat instances where the authenticatedUser is either the owningUsername or secondaryOwningUsername
     * @param owningUsername Maps to owningUsername of the MessageChat.
     * @param secondaryOwningUsername Maps to secondaryOwningUsername of the MessageChat
     * @return
     */
    List<MessageChat> findByOwningUsernameOrSecondaryOwningUsername(String owningUsername, String secondaryOwningUsername);
    MessageChat findByOwningUsernameAndSecondaryOwningUsername(String  owningUsername,String secondaryOwningUsername);
    List<MessageChat> findByOwningUsernameAndReadByOwningUsernameIsFalseOrSecondaryOwningUsernameAndReadBySecondaryOwningUsernameIsFalse(String owningUsername, String secondaryOwningUsername);
}
