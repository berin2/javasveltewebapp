import NotificationDto from "../NotificationDto";
import {MessageChatDto} from "../../Components/Messages/dto/MessageChatDto";
import SvelteUpUserAccountDto from "../profile/SvelteUpUserAccountDto";

class AppInitDto {
    authenticated:boolean;
    username:string;
    image: File;
    notifications: NotificationDto [];
    messages: MessageChatDto [];
    cart:object[];
    account: SvelteUpUserAccountDto;
    isEmailValidated: boolean;
    isIdentityVerified: boolean;

    constructor()
    {
        this.authenticated = false;
        this.notifications = [];
        this.messages = [];
        this.cart = [];
        this.listOfFoes = [];
    }

    updateSelf(jsonRes:object) : void
    {
        this.authenticated = true;
        this.username = jsonRes["username"];
        this.image = jsonRes["image"];
        this.isEmailValidated = jsonRes["isEmailValidated"];
        this.isIdentityVerified = jsonRes["isIdentityVerified"];
        this.messages = jsonRes["messages"];
        this.cart = [];
        this.account = jsonRes["account"];

        for(let notification of jsonRes["notifications"])
        {
            let dto: NotificationDto = new NotificationDto(notification);
            this.notifications.push(dto);
        }

    }
    deAuthenticate() : void
    {
        this.authenticated = false;
        this.username = null;
        this.image = null;
        this.isEmailValidated = null;
        this.isIdentityVerified = null;
        this.notifications = [];
        this.messages = [];
        this.listOfFoes = [];
        this.account = null;
    }
}

export default AppInitDto;