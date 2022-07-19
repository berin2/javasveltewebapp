package com.svelteup.app.backend.notificationmessage.models.message;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.PostMessageChatDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


/*
* MessageChain represents is a parent entity to Message HostDescriptor.
* It stores global properties that are relevant to a set of Message HostDescriptor
* sent between two users.
* */
@Entity
@Table(
       uniqueConstraints = {@UniqueConstraint(name = "pairedUserConstraint",columnNames = {"owningUsername","secondaryOwningUsername"})}
)
@Data()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class MessageChat extends PairedUserNonPrimaryKeyEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_chat_message_generator")
    @GenericGenerator(
            name="message_chat_message_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "message_chat_message_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "50"),
                    @Parameter(name = "optimizer", value = "pooled-lo")
            }
    )
    Long messageChatId;

    //tells us if there is unread messages for each  user.
    Boolean readByOwningUsername;
    Boolean readBySecondaryOwningUsername;

    Long owningUserEarliestAccessableMessage;
    Long secondaryOwningUsernameEarliestAccesableMessage;

    Integer pageCount;
    Long latestMessageIndex;
    String  latestMessage;


    public static final Long DELETED_CHAT_LONG_CONSTANT  = -1L;
    public static final String NULL_MESSAGE_IN_CHAT_EXCEPTION = "Username %s attempted to insert a null Message object in MessageChat with Long id  %d.";
    public static final String INVALID_MESSAGE_INDEX_EXCEPTION =  "Username %s attempted to insert a Message object whose message index is less than the previously inserted index in the message chat. Previous index: %d , New Index: %d.";
    public static final String ATTEMPTING_TO_DELETE_A_DELETED_CHAT = "Username %s attempted to delete chat with MessageChatId, but the chat  was already  marked as deleted for this user.";

    public MessageChat(PostMessageChatDto messageChatDto)
    {
       this(messageChatDto.owningUsername,messageChatDto.secondaryOwningUsername,messageChatDto.initialMessage);
    }
    public MessageChat(String creatingUserOne, String userTwo,String initialMessageContents)
    {
        super(creatingUserOne,userTwo);
        readByOwningUsername = true;
        this.readBySecondaryOwningUsername = false;
        this.owningUserEarliestAccessableMessage = 0L;
        this.secondaryOwningUsernameEarliestAccesableMessage  = 0L;
        this.setLatestMessage(initialMessageContents);
    }

    /**
     * deleteChatForOwningUsername deletes a chat for the owningusername will throw Http400Exception if the user attempts to delete an already
     * deleted Chat.
     * @param authenticatedUser the userwho is also the authenticated user attempting to delete a chat.
     * @throws Http500Exception if user is attempting to delete an already deleted chat.
     */


    /**
     *
     * @param authenticatedUser the username participatingin the chat who  would like to send a message.
     * @param newMessage the newly created and SAVED  message in the chat. Must have  a db id.
     * @throws NullPointerException throws NullPointerException if newMessage is null
     * @throws Http500Exception throws Http500Exception if newMessageMessageIndex is less than the current message chat latest index.
     */
    public void updateLatestMessageIndex(String authenticatedUser, Message newMessage) throws NullPointerException, Http500Exception
    {
        this.validateMessageIndex(authenticatedUser,newMessage);
        this.latestMessageIndex = newMessage.getMessageId();
    }

    /**
     * @param authenticatedUser  the user who wishes to send a message.
     * @param newMessage the message  which the user would like to  add to the chat.
     * @throws NullPointerException  NullPointerException if message is null
     * @throws Http500Exception Http500Exception if newMessageIndex is less than the latest chat index.
     */
    public void validateMessageIndex(String authenticatedUser, Message newMessage) throws NullPointerException, Http500Exception
    {
        Long newMessageMessageId = newMessage.getMessageId();

        if(newMessage == null)
            throw new NullPointerException(String.format(NULL_MESSAGE_IN_CHAT_EXCEPTION,authenticatedUser,this.messageChatId));
        else if(newMessageMessageId  <= this.latestMessageIndex)
            throw new Http500Exception(String.format(INVALID_MESSAGE_INDEX_EXCEPTION,authenticatedUser,this.latestMessageIndex,newMessageMessageId));
    }

    public boolean isOwningUser(String authenticatedUser)
    {
        boolean returnValue = false;
        returnValue = authenticatedUser.equals(this.getOwningUsername()) ? true : false;
        return returnValue;
    }

    public void deleteChatForUsername(String authenticatedUser)
    {
        if(authenticatedUser.equals(secondaryOwningUsername)) {
            this.secondaryOwningUsernameEarliestAccesableMessage = DELETED_CHAT_LONG_CONSTANT;
            this.readBySecondaryOwningUsername = null;
        }
        else if(authenticatedUser.equals(owningUsername)) {
            this.owningUserEarliestAccessableMessage = DELETED_CHAT_LONG_CONSTANT;
            this.readByOwningUsername = null;
        }
    }


    public boolean isDeletedForUsername(String authenticatedUser)
    {
        boolean retVal = false;
        if(authenticatedUser.equals(secondaryOwningUsername)) {
            retVal = readBySecondaryOwningUsername == null;
        }
        else if(authenticatedUser.equals(owningUsername)) {
            retVal = readByOwningUsername == null;
        }

        return  retVal;
    }

    public void updateChatReadStatus(String authenticatedUser, boolean chatBool)
    {
        if(owningUsername.equals(authenticatedUser))
            this.readByOwningUsername = chatBool;
        if(secondaryOwningUsername.equals(authenticatedUser))
            this.readBySecondaryOwningUsername = chatBool;
    }

    public  void  updateChatReadStatusForPairedUser(String authenticatedUser,boolean chatBool)
    {
        if(!owningUsername.equals(authenticatedUser))
            this.readBySecondaryOwningUsername = chatBool;
        if(secondaryOwningUsername.equals(authenticatedUser))
            this.readByOwningUsername = chatBool;
    }

}
