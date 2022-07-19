package com.svelteup.app.backend.notificationmessage.controllers.heartbeat;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.notificationmessage.dtos.HeartBeatDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.MessageChatDto;
import com.svelteup.app.backend.notificationmessage.dtos.NotificationDto;
import com.svelteup.app.backend.notificationmessage.services.messagechats.SMessageChat;
import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IControllerHeartBeatImpl {

    protected final SMessageChat sMessageChat;
    protected final SOrderStatusNotification sOrderStatusNotification;

    @GetMapping(ApplicationApi.GET_HEARTBEAT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<HeartBeatDto> getHeartBeatDto(@AuthenticationPrincipal SvelteUpUser user) throws OperationNotSupportedException, NotSupportedException {

        List<MessageChatDto> messageChatDtos = sMessageChat.getUnreadParticipatingMessageChatsForUser(user.getUsername(),null); //paging not impl yet.
        List<NotificationDto> notificationDtos = sOrderStatusNotification.getAllUnreadNotificationsForUsername(user.getUsername());
        HeartBeatDto discoveredShit = new HeartBeatDto(notificationDtos,messageChatDtos);

        return ResponseEntity.ok(discoveredShit);
    }
}
