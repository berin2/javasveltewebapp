package com.svelteup.app.backend.security.testing;

import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PostMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.message.PutMessageDto;
import com.svelteup.app.backend.notificationmessage.dtos.message.messagechat.PostMessageChatDto;
import com.svelteup.app.backend.notificationmessage.models.Notification;
import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import com.svelteup.app.backend.notificationmessage.repositories.RMessageChat;
import com.svelteup.app.backend.notificationmessage.repositories.RNotification;
import com.svelteup.app.backend.notificationmessage.services.messagechats.SMessageChat;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.modelcontroller.services.services.SProduct;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import com.svelteup.app.backend.productorder.models.ProductReview;
import com.svelteup.app.backend.productorder.repositories.RProductOrder;
import com.svelteup.app.backend.productorder.repositories.RProductReview;
import com.svelteup.app.backend.productorder.services.SProductOrderStatus;
import com.svelteup.app.backend.productorder.services.SProductReview;
import com.svelteup.app.backend.productorder.services.SProductReviewScoreCard;
import com.svelteup.app.backend.security.models.AccountAuthority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.repositories.AccountAuthorityRepository;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import com.svelteup.app.backend.security.repositories.AuthorityRepository;
import com.svelteup.app.backend.userlifecycle.services.initializerchains.RegistrationInitializerChain;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Component;

import javax.transaction.NotSupportedException;


@Component
@RequiredArgsConstructor
public class DataInitForTestingRunner  implements ApplicationRunner {
    private final RSvelteUpUser accountRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountAuthorityRepository accountAuthorityRepository;
    private final RNotification notificationRepo;
    private final SProduct productService;
    private final SProductOrderStatus productOrderStatusService;
    private final SProductReview productReviewService;
    private final SProductReviewScoreCard productReviewScoreCardService;
    private final RProductReview productReviewRepository;
    private final RProduct productRepository;
    private final RProductOrder productOrderRepository;
    private final RMessageChat messageChatRepository;
    private final SMessageChat messageChatService;
    private final SImageS3 S3service;
    private final RegistrationInitializerChain registrationInitializerChain;

    private final FindByIndexNameSessionRepository sessionRepository;

    private void initAuthority()
    {

        Authority fullySetup = new Authority(Authority.FULLY_SETUP_ACCOUNT);
        Authority notFullySetup = new Authority(Authority.NOT_FULLY_SETUP_ACCOUNT);

        authorityRepository.save(fullySetup);
    }
    private SvelteUpUser initUser()
    {
        Authority verified = authorityRepository.findAuthorityByAuthority(Authority.NOT_FULLY_SETUP_ACCOUNT);
        AccountAuthority test_user_authority = new AccountAuthority("test",verified);
        AccountAuthority test2_user_authority = new AccountAuthority("test2",verified);
        SvelteUpUser test_user = new SvelteUpUser("test","test");
        SvelteUpUser secondUser = new SvelteUpUser("test2","test2");
        accountRepository.saveAndFlush(test_user);
        accountRepository.saveAndFlush(secondUser);

       accountAuthorityRepository.saveAndFlush(test_user_authority);
       accountAuthorityRepository.saveAndFlush(test2_user_authority);
        return test_user;
    }


    private void initSocialProfile(SvelteUpUser user) throws NotSupportedException, InterruptedException {

    }

    private Product initProduct(SvelteUpUser user)
    {
        PostProductDto newProductJson = new PostProductDto();
        newProductJson.productName = "test";
        newProductJson.productAcceptsReturns = false;
        newProductJson.productCalories =400;
        newProductJson.productDescription="test description";
        newProductJson.productCost = 12.13;
        Product newProduct = new Product(user.getUsername(),newProductJson);
        newProduct = this.productRepository.saveAndFlush(newProduct);


        return newProduct;
    }

    private ProductOrder initProductOrder(SvelteUpUser user,Product productToOrder)
    {
        PostProductOrderDto newProductDto = new PostProductOrderDto();
        newProductDto.productId = productToOrder.getSurrogateId();
        newProductDto.productOrderQuantity = 2;

        ProductOrder createdOrder = new ProductOrder(user.getUsername(),productToOrder.getProductCost() * newProductDto.productOrderQuantity,2, ApplicationNotificationEnums.ORDER_PLACED,productToOrder);
        createdOrder = this.productOrderRepository.saveAndFlush(createdOrder);
        return createdOrder;
    }

    private ProductReview initProductReview(SvelteUpUser user, ProductOrder orderToReview)
    {
        PostProductReviewDto newReview = new PostProductReviewDto();
        newReview.productReviewStars = 4;
        newReview.productReviewTitle = "This is a test title";
        newReview.productReviewDescription = "This is a test product review description.";
        newReview.owningProductOrder = orderToReview.getSurrogateId();
        ProductReview productReviewObject = new ProductReview(user,newReview,orderToReview);
        return this.productReviewRepository.saveAndFlush(productReviewObject);
    }

    private MessageChat initMessageChat(SvelteUpUser owningUser, SvelteUpUser recievingUser)
    {
        PostMessageChatDto postMessageChatDto = new PostMessageChatDto();
        postMessageChatDto.owningUsername = owningUser.getUsername();
        postMessageChatDto.secondaryOwningUsername = recievingUser.getUsername();

        MessageChat newChat = new MessageChat(postMessageChatDto);
        this.messageChatRepository.saveAndFlush(newChat);
        return newChat;
    }

    private void initMessagesBetweenUsers(SvelteUpUser userOne, SvelteUpUser userTwo, MessageChat owningChat) throws NotSupportedException {
        PostMessageDto userOneSenderIterator = null;
        Message userOneSenderMessage = null;

        PostMessageDto userTwoSenderIterator = null;
        Message userTwoSenderMessage =  null;


        for(int i = 0; i < 150; i++)
        {
            userOneSenderIterator = new PutMessageDto();
            userOneSenderIterator.senderUsername = userTwo.getUsername();
            userOneSenderIterator.messageContent = String.format("This message has index %d with and is a test message.",i);
            userOneSenderIterator.messageChatId = owningChat.getSurrogateId();

            this.messageChatService.put(userOne.getUsername(),userOneSenderIterator);

            userTwoSenderIterator  = new PostMessageDto();
            userTwoSenderIterator.senderUsername = userOne.getUsername();
            userTwoSenderIterator.messageContent = String.format("This message has index %d with and is a test message.",i);
            userTwoSenderIterator.messageChatId = owningChat.getSurrogateId();

            this.messageChatService.put(userTwo.getUsername(),userTwoSenderIterator);
        }
    }

    public  void initNotifications(SvelteUpUser user)
    {
        for(int i =  0; i < 100; i++)
        {

            Notification newNotification = new Notification("SYSTEM",user.getUsername(),"Test Product", ApplicationNotificationEnums.ORDER_PLACED.ordinal());
            this.notificationRepo.save(newNotification);
        }
    }

    public void run(ApplicationArguments args) throws Exception {
        initAuthority();
        SvelteUpUser createdUser = initUser();
        SvelteUpUser seconduser = this.accountRepository.findById("test2").get();
        this.initSocialProfile(createdUser);
        this.initNotifications(createdUser);
       // SocialProfile createdProfile = this.initProfile(createdUser);
        //SocialProfile secondProvfile = this.initProfile(seconduser);
        Product createdProduct = this.initProduct(createdUser);
        Product secondProduct = this.initProduct(seconduser);
        ProductOrder createdProductOrder = this.initProductOrder(createdUser,secondProduct);
        ProductOrder secondCreatedProductOrder = this.initProductOrder(seconduser,createdProduct);
        ProductReview createdReview = this.initProductReview(createdUser,secondCreatedProductOrder);
        ProductReview secondCreatedReview=  this.initProductReview(seconduser,createdProductOrder);
        MessageChat newMessageChat = this.initMessageChat(createdUser,seconduser);
        MessageChat seconary = this.initMessageChat(seconduser,createdUser);

        this.initMessagesBetweenUsers(createdUser,seconduser,newMessageChat);
        newMessageChat = this.messageChatRepository.findById(newMessageChat.getMessageChatId()).get();
        newMessageChat.getOwningUsername();


    }
}
