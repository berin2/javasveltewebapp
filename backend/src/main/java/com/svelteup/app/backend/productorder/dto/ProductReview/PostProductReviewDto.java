package com.svelteup.app.backend.productorder.dto.ProductReview;

import com.svelteup.app.backend.productorder.dto.PutProductOrderDto;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@ApiModel(description = "Represents a JSON used to create ProductReviews.")
public class PostProductReviewDto {
    public String productReviewTitle;
    public String productReviewDescription;
    public Integer productReviewStars;
    public UUID owningProductOrder;
}
