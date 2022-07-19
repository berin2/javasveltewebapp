package com.svelteup.app.backend.testing.services.paireduserdatasetup;

import com.svelteup.app.backend.notificationmessage.dtos.message.message.PostMessageDto;
import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import com.svelteup.app.backend.notificationmessage.repositories.RMessage;
import com.svelteup.app.backend.notificationmessage.repositories.RMessageChat;
import com.svelteup.app.backend.notificationmessage.services.messagechats.SMessageChat;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.testing.dto.PairedOwningUserDataSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;

@Service
public class SMessageChatSetupOwningUser extends APairedOwningUserDataSetup {

    @Autowired RMessageChat messageChatRepo;
    @Autowired RMessage rMessage;
    @Autowired SMessageChat sMessageChat;

    @Override
    public void setupEntity(PairedOwningUserDataSetupDto dto) throws NotSupportedException, IOException, InterruptedException {


        MessageChat discoveredChat = sMessageChat.findOrCreateMessageChatByUsernameCombination(dto.chosenOwningUser.owningUser.getUsername(),dto.chosenSecondaryOwningUser.owningUser.getUsername(),"Initial MSG.");


        if(discoveredChat == null)
        {
            dto.messageChat = new MessageChat(dto.chosenOwningUser.owningUser.getUsername(),dto.chosenSecondaryOwningUser.owningUser.getUsername(),"Initial msg.");
            dto.messageChat = messageChatRepo.saveAndFlush(dto.messageChat);
            discoveredChat = messageChatRepo.findByOwningUsernameAndSecondaryOwningUsername(dto.chosenOwningUser.owningUser.getUsername(),dto.chosenSecondaryOwningUser.owningUser.getUsername());
        }
        else
            dto.messageChat = discoveredChat;


            Integer messageBound =  dto.getRandomIntegerWithinRange();
            SvelteUpUser sender = null;

                for (int i = 0; i < messageBound; i++) {
                    sender = i % 2 == 0 ? dto.chosenOwningUser.owningUser : dto.chosenSecondaryOwningUser.owningUser;
                    PostMessageDto postMessageDto = new PostMessageDto();
                    postMessageDto.messageChatId = dto.messageChat.getSurrogateId();
                    postMessageDto.messageContent = "Some message content. A nice cat name is " + this.fakeItTillYouMakeIt.cat().name() + "!!";
                    Message message = new Message(sender.getUsername(), dto.messageChat, postMessageDto);
                    message = rMessage.save(message);
                    dto.messageChat.setLatestMessage(message.getMessageContent());
                    dto.messageChat = messageChatRepo.save(dto.messageChat);
                }
            }

}
