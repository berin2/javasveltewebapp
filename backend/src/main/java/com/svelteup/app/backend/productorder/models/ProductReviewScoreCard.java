package com.svelteup.app.backend.productorder.models;

import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToPutDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserPrimaryKeySurrogateEntity;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
import com.svelteup.app.backend.productorder.dto.ProductReviewScoreCardDto.PutProductReviewScoreCardDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Data @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductReviewScoreCard extends OwningUserPrimaryKeySurrogateEntity implements ToPutDto<PutProductReviewScoreCardDto> {
    protected Integer fiveStarReviews;
    protected Integer fourStarReviews;
    protected Integer threeStarReviews;
    protected Integer twoStarReviews;
    protected Integer oneStarReviews;
    protected Integer totalOrders;

    public ProductReviewScoreCard(SvelteUpUser owningUser)
    {
        super(owningUser.getUsername());
        this.fiveStarReviews = 0;
        this.fourStarReviews = 0;
        this.threeStarReviews = 0;
        this.twoStarReviews = 0;
        this.oneStarReviews =0;
        this.totalOrders = 0;
    }

    @Override
    public void update(PutProductReviewScoreCardDto updateDto) {

    }

    @Override
    public PutProductReviewScoreCardDto toExistingDto() {
        PutProductReviewScoreCardDto returnDto = new PutProductReviewScoreCardDto(this);
        return returnDto;
    }

    public void updateReviewScoreCard(PostProductReviewDto postProductReviewDto)
    {
        Integer reviewStars = postProductReviewDto.productReviewStars;
        switch (reviewStars)
        {
            case 1:
                this.oneStarReviews  = oneStarReviews  +  1;
                break;
            case 2:
                this.twoStarReviews =twoStarReviews+ 1;
                break;
            case 3:
                this.threeStarReviews = threeStarReviews + 1;
                break;
            case 4:
                this.fourStarReviews = fourStarReviews + 1;
                break;
            case 5:
                this.fiveStarReviews = fiveStarReviews + 1;
                break;
        }

    }
}
