package com.svelteup.app.backend.notificationmessage.dtos.message.messagepage;

import io.swagger.annotations.ApiModel;

import java.util.UUID;

@ApiModel(value = "GetMessagePageDto represents the DTO used to pull Message pages from a message chat.")
public class GetMessagePageDto {
    public UUID messageChatId;
    public Integer messagePageNumber;
}
