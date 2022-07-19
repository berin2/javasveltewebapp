package com.svelteup.app.backend.notificationmessage.dtos.message.messagechat;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Represents JSON used to create a new MessageChat between two users.")
public class PostMessageChatDto {
    public String owningUsername;
    public String secondaryOwningUsername;
    public String initialMessage;
}
