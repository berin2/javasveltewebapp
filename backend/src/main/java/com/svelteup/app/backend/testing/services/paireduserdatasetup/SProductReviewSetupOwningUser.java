package com.svelteup.app.backend.testing.services.paireduserdatasetup;

import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
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
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SProductReviewSetupOwningUser extends APairedOwningUserDataSetup {

    @Autowired RProductOrder rProductOrder;
    @Autowired RProductReview rProductReview;
    @Autowired RProductReviewScoreCard rProductReviewScoreCard;
    @Autowired SProductOrder sProductOrder;

    @Override
    public void setupEntity(PairedOwningUserDataSetupDto dto) throws NotSupportedException {
        PostProductReviewDto postDto = new PostProductReviewDto();
        String owningUsername =  dto.chosenOwningUser.owningUser.getUsername();
        String secondaryOwningUsername = dto.chosenSecondaryOwningUser.owningUser.getUsername();
        ProductReviewScoreCard reviewScoreCard = this.rProductReviewScoreCard.findByOwningUsername(secondaryOwningUsername);
        Integer orderQuantiy = null;
        Integer reviewStars   = null;


        for(Product productToReview:dto.chosenSecondaryOwningUser.productList)
        {
            orderQuantiy = dto.getRandomIntegerWithinRange();
            ProductOrder order = new ProductOrder(owningUsername,orderQuantiy * productToReview.getProductCost(),orderQuantiy, ApplicationNotificationEnums.ORDER_ACCEPTED, productToReview);


            order = rProductOrder.save(order);


            reviewStars = ThreadLocalRandom.current().nextInt(1,6);
            postDto = new PostProductReviewDto();
            postDto.owningProductOrder = productToReview.getSurrogateId();
            postDto.productReviewStars = reviewStars;
            postDto.productReviewTitle = this.fakeItTillYouMakeIt.book().title();
            postDto.productReviewDescription = this.fakeItTillYouMakeIt.book().genre() + " and some description here!";

            reviewScoreCard.updateReviewScoreCard(postDto);
            rProductReviewScoreCard.save(reviewScoreCard);
            ProductReview review = new ProductReview(dto.chosenOwningUser.owningUser,postDto,order,productToReview);
            rProductReview.save(review);
        }
    }
    }
