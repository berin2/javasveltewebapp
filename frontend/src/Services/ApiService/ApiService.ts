//httpRequest(method:string, uri:string, headers:object, body:object, withCredentials:boolean, success:(...args:any[])=>any, fail:(...args:any[])=>any) {


class ApiService
{

    static AUTH_FULLY_SETUP = "/api/v1/auth/fully_setup";
    static ACCOUNT = ApiService.AUTH_FULLY_SETUP + "/account";

    static getDefaultHeaders() : object {
        return {
            "content-type":"application/json"
        }
    }
    static getAuthOnMountUrl(): string {
        return "/api/v1/auth/authenticate/onmount";
    }
    getLogOutUrl(): string {
        return "/api/v1/auth/login";
    }

    static getLoginUrl(): string {
        return "/api/v1/auth/login";
    }

    static getLogoutUrl():string {
        return "/api/v1/auth/logout";
    }
    static getRegisterUrl():string {
            return "/api/v1/register";
    }
    static getUnreadNotificationUrl(page:number):string {return `/api/v1/auth/fully_setup/notification/${page}`;}
    static getVerifyIdentityUrl() {return "/api/v1/auth/account/customer/verify";}

    //HeartBeat
    static getHeartBeatUrl():string{return '/api/v1/auth/fully_setup/heartbeat';}

    //FullyAuthURL
    static getProductPage(page:number) {return `${this.AUTH_FULLY_SETUP}/product/${page}`}
    static getProductUrl() {return `${this.AUTH_FULLY_SETUP}/product`;}
    static getProducts(username:string) : string {return `${this.AUTH_FULLY_SETUP}/product/all/${username}`;}
    static getPaymentTokenUrl(){return `${this.AUTH_FULLY_SETUP}/clientoken`;}
    static getPrefPaymentTokenUrl(){return `${this.AUTH_FULLY_SETUP}/clientoken`}

    //MessagesAndMessageChats
    static getMessagesForMessageChat(pageIndex:number, chatId:string){ return `/api/v1/auth/fully_setup/messagechat/message/${chatId}/${pageIndex}`}
    static getMessagePutPostDelete(){return '/api/v1/auth/fully_setup/messagechat/message'};

    //Account Urls
    static getPhoneNumberUrl():string { return ApiService.ACCOUNT + "account/phonenumber";}
    static getEmailUrl():string { return ApiService.ACCOUNT + "/email";}
    static getEmailTokenUrl():string {return ApiService.ACCOUNT + "/emailtoken";}
    static getPasswordUrl():string { return ApiService.ACCOUNT + "/password"};
    static getFoeListUrl():string { return ApiService.ACCOUNT + "/foes";}
    static getTwoFactorUrl():string { return ApiService.ACCOUNT + "/two_factor_auth";}
    static getAccountDeleteUrl():string { return ApiService.ACCOUNT + "delete";}

    //social profile
    static getSocialProfileUrl():string{return `${this.AUTH_FULLY_SETUP}/socialprofile`}

    //products


}

export default ApiService;