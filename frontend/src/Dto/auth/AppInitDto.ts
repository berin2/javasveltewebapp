import NotificationDto from "../NotificationDto";
import {MessageChatDto} from "../../Components/Messages/dto/MessageChatDto";
import SvelteUpUserAccountDto from "../profile/SvelteUpUserAccountDto";

class AppInitDto {
    authenticated:boolean;
    username:string;
    notifications: NotificationDto [];
    messages: MessageChatDto [];
    cart:object[];
    profile: SvelteUpUserAccountDto|null;
    isEmailValidated: boolean;
    isIdentityVerified: boolean;
    listOfFoes: string [];

    constructor()
    {
        this.authenticated = false;
        this.notifications = [];
        this.messages = [];
        this.cart = [];
        this.listOfFoes = [];
        this.profile = null;
    }

    updateSelf(jsonRes:object) : void
    {
        this.authenticated = true;
        this.username = jsonRes["username"];
        this.isEmailValidated = jsonRes["isEmailValidated"];
        this.isIdentityVerified = jsonRes["isIdentityVerified"];
        this.messages = jsonRes["messages"];
        this.cart = [];
        this.profile = jsonRes["profile"];

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
        this.isEmailValidated = null;
        this.isIdentityVerified = null;
        this.notifications = [];
        this.messages = [];
        this.listOfFoes = [];
        this.profile = null;
    }
}

export default AppInitDto;