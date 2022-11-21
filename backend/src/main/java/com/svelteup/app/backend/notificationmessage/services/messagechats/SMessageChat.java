package com.svelteup.app.backend.notificationmessage.services.messagechats;

import com.svelteup.app.backend.aop.aspects.paireduser.OwningUserPairedNonPkEntityAccessCheckAOPTarget;
import com.svelteup.app.backend.aop.aspects.paireduser.PPairedOwningUserNonPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.*;

import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUuidService;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserNonPk;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PostMessageDto;

import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.MessageChatDto;
import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import com.svelteup.app.backend.notificationmessage.repositories.RMessage;
import com.svelteup.app.backend.notificationmessage.repositories.RMessageChat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SMessageChat extends SSurrogateEntityOwningUserNonPk<Long, MessageChat> implements HttpUuidService<PostMessageDto, PostMessageDto> {

    protected RMessageChat messageChatRepository;
    protected RMessage messageRepository;
    protected PPairedOwningUserNonPkAccessChecker pPairedOwningUserNonPkAccessChecker;

    public final static String USER_CANNOT_PARTICIPATE_IN_CHAT_ERROR = "%s requested to be a part of MessageChat with surrogate id %s, but said user was not found in owningUsername or secondaryOwningUSername field.";
    public final static String BOTH_USERS_NOT_NULL  = "MessageChat with UUID %s was expected to have at least one user to equal null, but the application discovered neither user is null. authenticatedUser: %s , recieverUsername: %s .";
    public final static Sort MESSAGE_SORT = Sort.by("messageId").ascending();
    private final static Integer MESSAGE_PAGE_SIZE = 10;

    public SMessageChat(RMessageChat surrogateJpaRepository, RMessage messageRepository, PPairedOwningUserNonPkAccessChecker pPairedOwningUserNonPkAccessChecker)
    {
        super(surrogateJpaRepository);
        this.messageChatRepository  = surrogateJpaRepository;
        this.messageRepository = messageRepository;
        this.pPairedOwningUserNonPkAccessChecker = pPairedOwningUserNonPkAccessChecker;
    }

    @Override
    public void post(String authenticatedUser, PostMessageDto newMessage) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException
    {
        MessageChat newMessageChat = this.findOrCreateMessageChatByUsernameCombination(authenticatedUser,newMessage.senderUsername,newMessage.messageContent);
        this.accessCheck(authenticatedUser,newMessageChat);
        postMessageToChat(authenticatedUser,newMessageChat,newMessage);
    }

    public void postMessageToChat(String authenticatedUser,MessageChat chatToPostTo, PostMessageDto postDto)
    {
        Message newMessage = new Message(authenticatedUser,chatToPostTo,postDto);
        newMessage = this.messageRepository.save(newMessage);
        chatToPostTo.setLatestMessage(newMessage.getMessageContent());
        chatToPostTo.setLatestMessageIndex(newMessage.getMessageId());
        chatToPostTo.updateChatReadStatusForPairedUser(authenticatedUser,true);
        this.messageChatRepository.save(chatToPostTo);
    }

    public MessageChat findOrCreateMessageChatByUsernameCombination(String authenticatedOwningUser, String secondaryUsername,String initialMessage)
    {
        MessageChat authUserIsOwningUser = this.messageChatRepository.findByOwningUsernameAndSecondaryOwningUsername(authenticatedOwningUser,secondaryUsername);
        MessageChat recieverIsOwningUser = this.messageChatRepository.findByOwningUsernameAndSecondaryOwningUsername(secondaryUsername,authenticatedOwningUser);
        MessageChat returnChat = null;

        if(authUserIsOwningUser != null && recieverIsOwningUser != null)
            throw new Http500Exception(String.format(BOTH_USERS_NOT_NULL,authUserIsOwningUser,recieverIsOwningUser));
        else if(authUserIsOwningUser != null)
            returnChat =  authUserIsOwningUser;
        else if(recieverIsOwningUser != null)
            returnChat = recieverIsOwningUser;
        else {
            returnChat = new MessageChat(authenticatedOwningUser,secondaryUsername,initialMessage);
            this.messageChatRepository.save(returnChat);
        }
        return returnChat;
    }

    @Override
    public ResponseEntity<PostMessageDto> get(UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception {
       throw new UnsupportedOperationException("get(UUID secondary_id) is not supported in the class  " + this.getClass().toString());
    }


    public ResponseEntity<List<PostMessageDto>> getMessageChatsForUser(String authenticatedUser, UUID messageChatId, Integer pageIndex) throws NotSupportedException {
        Pageable requestedPage  = PageRequest.of(pageIndex,MESSAGE_PAGE_SIZE, MESSAGE_SORT);
        MessageChat discoveredChat = this.findBySurrogateIdSecondaryOwningUserCheck(authenticatedUser,messageChatId);
        Page<Message> discoveredChats = null;
        List<PostMessageDto> discoveredMessageDtos = new ArrayList<>();

        //get latest chats for owninguser and set read status to true.
        discoveredChats = this.messageRepository.findByOwningMessageChatSurrogateIdAndMessageIdAfter(discoveredChat.getSurrogateId(), discoveredChat.getOwningUserEarliestAccessableMessage(), requestedPage);
        discoveredChat.setReadByOwningUsername(true);

        this.messageChatRepository.save(discoveredChat);

        for(Message pageMessage : discoveredChats)
            discoveredMessageDtos.add(new PostMessageDto(discoveredChat,pageMessage));

        return ResponseEntity.ok(discoveredMessageDtos);
    }

    public List<MessageChatDto> getParticipatingMessageChatsForUser(String authenticatedUser, Integer pageIndex)
    {
        List<MessageChat> discoveredChats  = this.messageChatRepository.findByOwningUsernameOrSecondaryOwningUsername(authenticatedUser,authenticatedUser);
        List<MessageChatDto> returnList = new ArrayList<>();
        MessageChatDto dtoIterator = null;

          for(MessageChat chat : discoveredChats)
            if(!chat.isDeletedForUsername(authenticatedUser))
            {
                dtoIterator = new MessageChatDto(authenticatedUser,chat);
                chat.updateChatReadStatus(authenticatedUser,true);
                returnList.add(dtoIterator);
            }

        return returnList;
    }
    public List<MessageChatDto> getUnreadParticipatingMessageChatsForUser(String authenticatedUser, Integer pageIndex)
    {
        List<MessageChat> discoveredChats  = this.messageChatRepository
                .findByOwningUsernameAndReadByOwningUsernameIsFalseOrSecondaryOwningUsernameAndReadBySecondaryOwningUsernameIsFalse(authenticatedUser,authenticatedUser);

        List<MessageChatDto> returnList = new ArrayList<>();
        MessageChatDto dtoIterator = null;

        for(MessageChat chat : discoveredChats)
        {
            dtoIterator = new MessageChatDto(authenticatedUser,chat);
            chat.updateChatReadStatus(authenticatedUser,true);
            repository.save(chat);
            returnList.add(dtoIterator);
        }

        return returnList;
    }


    @Override
    public void put(String authenticated_user, PostMessageDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        MessageChat discoveredMessageChat = this.findBySurrogateIdSecondaryOwningUserCheck(authenticated_user,update_DTO.messageChatId);
        Message newMessageChatMessage = new Message(authenticated_user,discoveredMessageChat,update_DTO);
        this.messageRepository.save(newMessageChatMessage);
    }

    @Override
    public void delete(String authenticatedUser, UUID secondary_id) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException
    {
        MessageChat discoveredChat  = this.findBySurrogateIdOwningUserCheck(authenticatedUser,secondary_id);
        if(!discoveredChat.isDeletedForUsername(authenticatedUser))
        {
            discoveredChat.deleteChatForUsername(authenticatedUser);
            this.repository.save(discoveredChat);
        }
    }




    protected boolean isOwningUser(MessageChat messageChat,String authenticatedUser)
    {
        return authenticatedUser.equals(messageChat.getOwningUsername());
    }
}
