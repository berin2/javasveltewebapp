package com.svelteup.app.backend.notificationmessage.dtos.message.message;

import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import com.svelteup.app.backend.utils.dtos.SurrogateDto;
import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;

import java.util.UUID;


@ApiModel(description = "Represents JSON dto used to transmit new messages without message chains from the front end to the backend.")
@NoArgsConstructor
public class PostMessageDto extends SurrogateDto {
    public String messageContent;
    public String senderUsername;
    public UUID messageChatId;

    public PostMessageDto(MessageChat messageChat, Message message)
    {
        super(message.getSurrogateId());
        this.messageContent = message.getMessageContent();
        this.senderUsername = message.getOwningUsername();
        this.messageChatId = messageChat.getSurrogateId();
    }

    public PostMessageDto(Message sourceMessage)
    {
        this.messageContent = sourceMessage.getMessageContent();
        this.senderUsername = sourceMessage.getOwningUsername();
        this.messageChatId = sourceMessage.getOwningMessageChatSurrogateId();
        this.surrogateId = sourceMessage.getSurrogateId();
    }
}
