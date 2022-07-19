package com.svelteup.app.backend.notificationmessage.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.notificationmessage.models.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RMessage extends RSurrogateJpaRepository<Message,Long>
{
    public Page<Message> findByOwningUsernameAndMessageIdAfter(String owningUsername, Long startingMessageIndex, Pageable pageDetails);
    public List<Message> findByOwningUsername(String owningUsername);
    public Page<Message> findByOwningMessageChatSurrogateIdAndMessageIdAfter(UUID id,Long startingMessageIndex, Pageable pageDetails);
}