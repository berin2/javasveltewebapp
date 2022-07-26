package com.svelteup.app.backend.dtostores;

import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.MessageChatDto;
import com.svelteup.app.backend.notificationmessage.dtos.NotificationDto;
import com.svelteup.app.backend.profile.dtos.SvelteUpUserProfileDto;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(value = "AppInitDto represents dto used carry user information for authentication purposes")
public class AppInitDto {
    public String username;
    public boolean isEmailValidated;
    public boolean isIdentityVerified;
    public List<NotificationDto> notifications;
    public List<MessageChatDto> messages;
    public SvelteUpUserProfileDto profile;
}
