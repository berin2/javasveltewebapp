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
        Authority verified = authorityRepository.findAuthorityByAuthority(Authority.FULLY_SETUP_ACCOUNT);
        AccountAuthority test_user_authority = new AccountAuthority("SYSTEM_USER",verified);
        if(accountRepository.findById(SvelteUpUser.SYSTEM_USER).get() != null)
        {
            SvelteUpUser test_user = new SvelteUpUser(SvelteUpUser.SYSTEM_USER,"fsjfllfkdjj231fdlkfjkds32");
            accountRepository.saveAndFlush(test_user);
            accountAuthorityRepository.saveAndFlush(test_user_authority);
        }

        return null;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
