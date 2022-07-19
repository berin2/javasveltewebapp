package com.svelteup.app.backend.notificationmessage.models.message;

import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToPutDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PostMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PutMessageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


/*
* Message is an entity
* */
@Entity
@Data()
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor()
public class Message extends OwningUserNonPrimaryKeySurrogateEntity implements ToPutDto<PostMessageDto>
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long messageId;
    private String messageContent;
    private Timestamp messageTimestamp;
    @Column(insertable = true,nullable = false,updatable = false)
    private UUID owningMessageChatSurrogateId;

    public Message(String authenticatedSender, MessageChat owningMessageChat, PostMessageDto updateMessage)
    {
        super(authenticatedSender);
        this.messageContent = updateMessage.messageContent;
        this.messageTimestamp = new Timestamp(System.currentTimeMillis());
        this.owningMessageChatSurrogateId = owningMessageChat.getSurrogateId();
    }

    @Override
    public void update(PostMessageDto updateDto) {
        this.messageContent  = updateDto.messageContent;
    }

    @Override
    public PostMessageDto toExistingDto() {
        return new PostMessageDto(this);
    }

}
