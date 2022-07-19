package com.svelteup.app.backend.notificationmessage.dtos;

import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.MessageChatDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;

import java.util.List;

@ApiModel("HeartBeatDto is used to store and return data that subject to frequent change, such as notifications and user messagechats.")
@AllArgsConstructor
public class HeartBeatDto {
    public List<NotificationDto> notifications;
    public List<MessageChatDto> messages;
}
