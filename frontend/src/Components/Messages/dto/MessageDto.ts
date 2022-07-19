import {isFalsy} from "../../../Validators/IsFalsyObjectValidator";

class MessageDto
{
    public messageContent:string;
    public senderUsername:string;
    public messageChatId:string;

    constructor(arg:object) {

        if(!isFalsy(arg))
        {
            this.messageContent = !isFalsy(arg["messageContent"]) ? arg["messageContent"] : "";
            this.senderUsername = !isFalsy("senderUsername") ? arg["senderUsername"] : "";
            this.messageChatId = !isFalsy("messageChatId") ? arg["messageChatId"] : "";
        }
    }

}

export default MessageDto;