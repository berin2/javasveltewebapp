package com.svelteup.app.backend.testing.dto;

import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.notificationmessage.models.message.Message;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.models.ProductReview;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.security.models.SvelteUpUser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PairedOwningUserDataSetupDto extends BaseDataSetupDto {


    public PairedOwningUserDataSetupDto(Integer minimum, Integer maximum)
    {
        super(minimum,maximum);
    }

    public OwningUserDataSetupDto chosenOwningUser;
    public OwningUserDataSetupDto chosenSecondaryOwningUser;

    public MessageChat messageChat;



    public List<ProductQuestion> productQuestion;
    public List<Product> productList;
    public List<ProductOrder> productOrderList;
    public List<ProductReview> productReviews;

    public List<MessageChat> messageChats;
    public List<Message> owningUserMessageChats;
    public List<Message> secondaryOwningUserMessageChats;

}
