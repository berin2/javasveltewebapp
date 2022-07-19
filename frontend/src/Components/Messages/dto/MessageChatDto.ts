import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

class MessageChatDto
{
    public messageChatId:string;
    public senderUsername:string;
    public readByOwner:boolean;
    public latestMessage:string;

    public constructor(arg:object)
    {
        if(!isFalsy(arg))
        {
            if(!isFalsy(arg["messageChatId"]))
               this.messageChatId = arg["messageChatId"];
            if(!isFalsy(arg["senderUsername"]))
                this.senderUsername = arg["senderUsername"];
            if(!isFalsy(arg["readByOwner"]))
                this.senderUsername = arg["readByOwner"];
        }
    }
}

export {MessageChatDto};