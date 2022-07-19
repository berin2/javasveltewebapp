package com.svelteup.app.backend.productorder.dto.ProductReviewScoreCardDto;

import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import io.swagger.annotations.ApiModel;

import java.util.UUID;

@ApiModel(description = "Represents a JSON used to get a users ProductReviewScoreCard.")
public class PutProductReviewScoreCardDto {
    public Integer fiveStarReviews;
    public Integer fourStarReviews;
    public Integer threeStarReviews;
    public Integer twoStarReviews;
    public Integer oneStarReviews;
    public Integer totalOrders;

    public PutProductReviewScoreCardDto(ProductReviewScoreCard productReviewScoreCard)
    {
        this.fiveStarReviews = productReviewScoreCard.getFiveStarReviews();
        this.fourStarReviews = productReviewScoreCard.getFourStarReviews();
        this.threeStarReviews = productReviewScoreCard.getThreeStarReviews();
        this.twoStarReviews = productReviewScoreCard.getTwoStarReviews();
        this.oneStarReviews = productReviewScoreCard.getOneStarReviews();
        this.totalOrders = -productReviewScoreCard.getTotalOrders();
    }
}
