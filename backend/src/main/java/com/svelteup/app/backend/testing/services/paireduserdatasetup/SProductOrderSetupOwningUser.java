package com.svelteup.app.backend.testing.services.paireduserdatasetup;

import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.notificationmessage.events.NotificationEvent;
import com.svelteup.app.backend.notificationmessage.services.notifications.SOrderStatusNotification;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.models.ProductReview;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.productorder.repositories.RProductOrder;
import com.svelteup.app.backend.productorder.repositories.RProductReview;
import com.svelteup.app.backend.productorder.repositories.RProductReviewScoreCard;
import com.svelteup.app.backend.productorder.services.SProductOrder;
import com.svelteup.app.backend.testing.dto.PairedOwningUserDataSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SProductOrderSetupOwningUser extends APairedOwningUserDataSetup {
    @Autowired RProduct productRepository;
    @Autowired RProductOrder rProductOrder;
    @Autowired SOrderStatusNotification sOrderStatusNotification;
    @Autowired SProductOrder sProductOrder;
    @Autowired RProductReviewScoreCard rProductReviewScoreCard;

    @Override
    public void setupEntity(PairedOwningUserDataSetupDto dto) throws NotSupportedException, IOException, InterruptedException {

        PostProductOrderDto postProductOrderDto = new PostProductOrderDto();
        ProductReviewScoreCard scoreCard;

        for(Product productToOrder: dto.chosenSecondaryOwningUser.productList)
        {

            ProductOrder productOrder = new ProductOrder(dto.chosenSecondaryOwningUser.owningUser.getUsername(), productToOrder.getProductCost(), dto.getRandomIntegerWithinRange(), ApplicationNotificationEnums.randomEnumValue(),productToOrder);
            NotificationEvent event = new NotificationEvent(productOrder,dto.chosenSecondaryOwningUser.owningUser.getUsername(),productToOrder.getProductName(), dto.chosenOwningUser.owningUser.getUsername(), ApplicationNotificationEnums.ORDER_PLACED);
            sOrderStatusNotification.postNotificationEvent(event);
            rProductOrder.save(productOrder);
            scoreCard = rProductReviewScoreCard.findByOwningUsername(dto.chosenSecondaryOwningUser.owningUser.getUsername());
            scoreCard.setTotalOrders(scoreCard.getTotalOrders() + 1);
            rProductReviewScoreCard.save(scoreCard);
        }
    }
}