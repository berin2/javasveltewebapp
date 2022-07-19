package com.svelteup.app.backend.notificationmessage.controllers.message;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PostMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PutMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.MessageChatDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.util.List;

public interface IMessageController extends HttpController<PostMessageDto,PutMessageDto, UuidDto>
{
    @GetMapping(value = ApplicationApi.GET_MESSAGE_CHAT_MESSAGES, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<PostMessageDto>> get(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @PathVariable("messageChatSurrogateId") String messageChatSurrogateId,@PathVariable Integer pageIndex) throws NotSupportedException;
    @GetMapping(value = ApplicationApi.GET_MESSAGE_CHATS,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<MessageChatDto>> getMessageChats(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @PathVariable Integer pageIndex);
    @PostMapping(ApplicationApi.POST_PUT_DELETE_MESSAGE_TO_MESSAGE_CHAT)
    @ResponseStatus(HttpStatus.OK)
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PostMessageDto postDto) throws Http405Exception,NotSupportedException;
    @PutMapping(ApplicationApi.POST_PUT_DELETE_MESSAGE_TO_MESSAGE_CHAT)
    @ResponseStatus(HttpStatus.OK)
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PutMessageDto putDto) throws Http400Exception,NotSupportedException;
    @DeleteMapping(ApplicationApi.POST_PUT_DELETE_MESSAGE_TO_MESSAGE_CHAT)
    @ResponseStatus(HttpStatus.OK)
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody UuidDto deleteDto) throws Http405Exception, NotSupportedException;
}


