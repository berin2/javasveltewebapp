package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.productorder.repositories.RProductReviewScoreCard;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class SProductReviewCard extends AOwningUserDataSetup {

    @Autowired RProductReviewScoreCard rReviewScoreCard;
    @Override
    public void setupEntity(OwningUserDataSetupDto dto) {
        ProductReviewScoreCard reviewScoreCard  = new ProductReviewScoreCard(dto.owningUser);
        dto.owningUserProductReviewScoreCard = rReviewScoreCard.save(reviewScoreCard);
    }
}
