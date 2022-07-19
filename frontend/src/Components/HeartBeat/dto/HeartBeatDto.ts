import type NotificationDto from "../../../Dto/NotificationDto";
import type {MessageChatDto} from "../../Messages/dto/MessageChatDto";
import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

class HeartBeatDto
{
    public notifications: NotificationDto[];

    public messages: MessageChatDto[];
    public messageIndexMap: Map<String,Number>;
    public isCompletelyEmpty:Boolean;

    public constructor() {
        this.isCompletelyEmpty= true;
        this.messageIndexMap = new Map<String,Number>();
    }

    public updateSelf(apiArg:object):void
    {
        let unreadNotifications:NotificationDto[] = apiArg["notifications"];
        let unreadMessageChats: MessageChatDto[] =apiArg["messages"];

        if(this.isCompletelyEmpty)
        {
            if (!isFalsy(unreadNotifications))
                this.notifications = unreadNotifications;
            if (!isFalsy(apiArg["messages"]))
                this.messages = unreadMessageChats;
        }
        else
        {
            if (!isFalsy(unreadNotifications))
                this.notifications = [...unreadNotifications, ... this.notifications];
            if (!isFalsy(apiArg["messages"]))
                this.updateMessageChatsWhenStoreNotEmpty(apiArg["messages"]);
        }
        this.isCompletelyEmpty = false;
        this.sortSelf();
        this.updateMessageChatIndexMap();
    }

    protected sortSelf():void
    {
        if(!isFalsy(this.messages))
            this.messages.sort((a,b) => {return a.senderUsername   > b.senderUsername ? 1 : -1;});
    }

    protected updateMessageChatIndexMap() : void
    {
        this.messageIndexMap.clear();

        for(let messageIndex in this.messages)
            this.messageIndexMap.set(this.messages[messageIndex].messageChatId, Number(messageIndex));
    }

    protected updateMessageChatsWhenStoreNotEmpty(msgChats:MessageChatDto[]) : void
    {
        let messageChatDto: MessageChatDto;
        let discoveredElementIndex:Number|undefined = undefined;

        for(let msgChat of msgChats)
            if(!isFalsy(msgChat) && !isFalsy(msgChat.messageChatId))
            {
                discoveredElementIndex = this.messageIndexMap.get(msgChat.messageChatId);
                if(discoveredElementIndex != undefined)
                    this.messages[discoveredElementIndex.valueOf()] = msgChat;
                else
                    this.messages.push(msgChat);
            }
    }

    public deleteMessageChat(id:string)
    {
        let index: Number | undefined = this.messageIndexMap.get(id);

        if(index != undefined)
        {
            this.messages.splice(index.valueOf(),1);
            this.updateMessageChatIndexMap()
        }
    }

}

export  default HeartBeatDto;