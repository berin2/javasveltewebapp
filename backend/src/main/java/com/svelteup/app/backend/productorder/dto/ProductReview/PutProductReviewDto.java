package com.svelteup.app.backend.productorder.dto.ProductReview;

import io.swagger.annotations.ApiModel;

import java.time.LocalDate;
import java.util.UUID;

@ApiModel(description = "Represents a JSON used to create ProductReviews.")
public class PutProductReviewDto extends PostProductReviewDto {
    public UUID productReviewId;
    public String productReviewDateTimeStamp;
    public String  owningUser;
}
