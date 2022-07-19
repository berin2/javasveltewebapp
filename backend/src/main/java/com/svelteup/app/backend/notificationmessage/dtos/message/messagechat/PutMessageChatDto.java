package com.svelteup.app.backend.notificationmessage.dtos.message.messagechat;

import com.svelteup.app.backend.notificationmessage.dtos.message.message.PutMessageDto;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel(description = "Represents JSON used to update a new MessageChat between two users.")
public class PutMessageChatDto {
    @ApiModelProperty(value = "Represents the Java UUID of the chat to update.")
    public UUID messageChatId;
    @ApiModelProperty(value = "Represents the earliest Read timestamp ")
    public UUID requestingUsersTimestampValue;
    public PutMessageDto messageDto;

}
