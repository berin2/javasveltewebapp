package com.svelteup.app.backend.notificationmessage.controllers.message;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PostMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PutMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.MessageChatDto;
import com.svelteup.app.backend.notificationmessage.services.messagechats.SMessageChat;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.UUID;

@RestController()
@AllArgsConstructor()
public class MessageController implements IMessageController {
    SMessageChat  messageChatService;


    /**
     * get method returns a JSON list of messages for a particular chat between two users.
     * @param authenticatedUser The authenticated user to get message chats for.
     * @param messageChatId the messageChatId to get the messages back for.
     * @param pageIndex    The chat pageIndex to return back to the  user.
     * @return  Returns List<PutMessageDto> which contains a list of messges for the chat.
     * @throws NotSupportedException
     */
    @Override
    public ResponseEntity<List<PostMessageDto>> get(SvelteUpUser authenticatedUser, String messageChatId, Integer pageIndex) throws NotSupportedException
    {
        UUID messageChatUUIDId = UUID.fromString(messageChatId);
        ResponseEntity<List<PostMessageDto>> returnList = this.messageChatService.getMessageChatsForUser(authenticatedUser.getUsername(),messageChatUUIDId,pageIndex);
        return returnList;
    }

    /**
     * getMessageChats is used to fetch
     * @param authenticatedUser The authenticated user for whom to fetch the MessageChats for.
     * @param pageIndex The pageIndex of the set of chats to fetch.
     * @return
     */
    @Override
    public ResponseEntity<List<MessageChatDto>> getMessageChats(SvelteUpUser authenticatedUser, Integer pageIndex) {
        List<MessageChatDto> returnDtos = this.messageChatService.getParticipatingMessageChatsForUser(authenticatedUser.getUsername(),pageIndex);
        return ResponseEntity.ok(returnDtos);
    }

    /**
     * post is used to create a new message chat between two users, and it is used to post the initial message between the two users.
     * Whoever the sending the user is will also taken the owningUsername value of the message chat.Whoever is the recievingUser of the
     * message will take the secondary ownerUsername of the chat.
     * @param authenticatedUser The currently authenticatedUser who is the message sender and chat creator.
     * @param postMessageDto    The message to send in the newly created chat.
     * @throws Http405Exception,Http403Exception Will not throw 405 as method is supported. Should not throw 403 as this is a new chat being created.
     * @throws NotSupportedException Should not throw NotSupportedException as method call is Supported unless user is attempting to post to
     * an already existing chat.
     */
    @Override
    public void post(SvelteUpUser authenticatedUser, PostMessageDto postMessageDto) throws Http405Exception, Http403Exception, NotSupportedException {
        this.messageChatService.post(authenticatedUser.getUsername(),postMessageDto);
    }

    /**
     *put is used to update a currently existing MessageChat.If attempting to put to a nonexisting, the method will throw a NotSupportedException.
     * @param authenticatedUser The user who wishes to send a message in an existing MessageChat.
     * @param putMessageDto The message dto containing the MessageChat SurrogateId and message content which we wish to post.
     * @throws Http400Exception Throws 400 if request body is not the proper format.
     * @throws NotSupportedException Throws NotSupportedException if the the user is trying to put to a nonexisting chat.
     */
    @Override
    public void put(SvelteUpUser authenticatedUser, PutMessageDto putMessageDto) throws Http400Exception, NotSupportedException {
        this.messageChatService.put(authenticatedUser.getUsername(),putMessageDto);
    }

    /**
     * CURRENTLY NOT SUPPORTED TBD.
     * @param authenticatedUser
     * @param uuidDto
     * @throws Http405Exception
     * @throws NotSupportedException
     */
    @Override
    public void delete(SvelteUpUser authenticatedUser, UuidDto uuidDto) throws Http405Exception, NotSupportedException {
        this.messageChatService.delete(authenticatedUser.getUsername(),uuidDto.id);
    }
}
