package com.svelteup.app.backend.api;

import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/*
* Application Api defines the application's api and returns a Set<String> containing
* the api which can also be converted into a Json.
* */
@Component
public class ApplicationApi {

    public static final String DEV_PROFILE = "dev";
    public static final String PROD_PROFILE  =  "prod";

    public static final String HTTP_POST = "POST";
    public static final String HTTP_DELETE = "DELETE" ;


    public static final String AUTH_CONST = "auth";
    public static final String AUTH_FULLY_SETUP = "/api/v1/auth/fully_setup";

    //ILoginLogoutRegisterController endpoints START
    public static final String GET_AUTHENTICATE = "/api/v1/auth/authenticate/";
    public static final String GET_AUTH_ON_MOUNT = "/api/v1/auth/authenticate/onmount" ;
    public static final String LOGIN = "/api/v1/auth/login";
    public static final String LOGOUT = "/api/v1/auth/logout";
    //ILoginLogoutRegisterController endpoints END

    //Product endpoints START
    public static final String GET_ALL_PRODUCTS =  AUTH_FULLY_SETUP + "/product/all/{ownerusername}";
    public static final String PRODUCT   = AUTH_FULLY_SETUP + "/product";
    public static final String PRODUCT_ORDER =  AUTH_FULLY_SETUP + "/product/productorder";
    public static final String PRODUCT_REVIEW = AUTH_FULLY_SETUP + "/product/productreview";
    public static final String PRODUCT_QUESTION = AUTH_FULLY_SETUP + "/product/productquestion";
    public static final String GET_PRODUCT_QUESTION_PAGE = AUTH_FULLY_SETUP + "/product/productquestion/{pageIndex}";
    //Product endpoints END

    //Account Related EndPointsStart
    public static final String PUT_ACCOUNT_EMAIL =  "/api/v1/auth/account/email";
    public static final String PUT_ACCOUNT_VERIFICATION_TOKEN = "/api/v1/auth/account/emailtoken";
    public static final String ACCOUNT_PASSWORD =  "/api/v1/auth/account/password";

    public static final String ACCOUNT_PHONE =  "/api/v1/auth/account/phone";
    public static final String ACCOUNT_ADDRESS =  "/api/v1/auth/account/password";
    public static final String ACCOUNT_SOCIALPROFILE = AUTH_FULLY_SETUP + "/socialprofile";
    //

    //Notifications START
    public static final String GET_NOTIFICATIONS = AUTH_FULLY_SETUP + "/notification/{pageIndex}";
    public static final String GET_HEARTBEAT = AUTH_FULLY_SETUP + "/heartbeat";
    //Notifications END

    //Messages START
    public static final String GET_MESSAGE_CHATS =  AUTH_FULLY_SETUP + "/messagechat/{pageIndex}";
    public static final String GET_MESSAGE_CHAT_MESSAGES = AUTH_FULLY_SETUP + "/messagechat/message/{messageChatSurrogateId}/{pageIndex}";
    public static final String POST_PUT_DELETE_MESSAGE_TO_MESSAGE_CHAT = AUTH_FULLY_SETUP +"/messagechat/message";
    //Messages END

    //Payment START
     public static final String CLIENT_TOKEN =  AUTH_FULLY_SETUP + "/clientoken";
     public static final String CLIENT_PREF_PAYMENT = AUTH_FULLY_SETUP + "/preferredpayment";
    //Payment END

    public static final String PRODUCT_ORDER_STATUS = PRODUCT_ORDER +"/status" ;

    //InitializerRoutes Start
    public static final String POST_CUSTOMER_INITIALIZE  = "/api/v1/auth/account/customer/verify";
    public static final String POST_PHONE_NUMBER_VERIFY = "/api/v1/auth/account/phonenumber/verify";
    public static final String POST_EMAIL_VERIFY =  "/api/v1/noauth/account/emailverify";
    public static final String GET_EMAIL_VERIFY = "/api/v1/noauth/account/emailverify/{emailToken}";
    public static final String REGISTER =  "/api/v1/register";
    //InitializerRoutesStart End


    //Account Routes Start
    public static final String ACCOUNT = AUTH_FULLY_SETUP + "/account";
    public static final String POST_PHONE_NUMBER = ACCOUNT + "/phonenumber";
    public static final String POST_EMAIL = ACCOUNT + "/email";
    public static final String POST_FOE = ACCOUNT  + "/foes";
    public static final String TWO_FACTOR_AUTH = ACCOUNT + "/two_factor_auth";
    public static final String PASSWORD = ACCOUNT + "/password";
    //Account Routes End

    public  Set<String> getApi()
    {
        Set<String> apiJson = new LinkedHashSet<>();
        apiJson.add(REGISTER);
        apiJson.add(LOGIN);
        apiJson.add(LOGOUT);

        return apiJson;
    }

}
