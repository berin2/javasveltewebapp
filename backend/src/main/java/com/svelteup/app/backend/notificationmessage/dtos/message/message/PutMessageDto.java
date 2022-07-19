package com.svelteup.app.backend.notificationmessage.dtos.message.message;

import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@ApiModel(description = "Represents JSON used to add a new message to an existing message chain.")
@NoArgsConstructor
public class PutMessageDto extends PostMessageDto {
}
