package com.svelteup.app.backend.notificationmessage.dtos.message.messagechat;

import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import io.swagger.annotations.ApiModel;

import java.util.UUID;

@ApiModel(description = "MessageChatDto represents JSON used to transmit MessageChat information to the frontn end.")
public class MessageChatDto
{
    public UUID messageChatId;
    public String senderUsername;
    public Boolean readByOwner;
    public String latestMessage;


    public MessageChatDto(String authenticatedUser, MessageChat messageChat)
    {
        this.messageChatId = messageChat.getSurrogateId();
        if(messageChat.isOwningUser(authenticatedUser))
        {
            this.readByOwner = messageChat.getReadBySecondaryOwningUsername();
            this.senderUsername = messageChat.getSecondaryOwningUsername();
        }
        else
        {
            this.readByOwner = messageChat.getReadByOwningUsername();
            this.senderUsername = messageChat.getOwningUsername();
        }
        this.latestMessage = messageChat.getLatestMessage();
    }
}
