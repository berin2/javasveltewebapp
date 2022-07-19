package com.svelteup.app.backend.productorder.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;

public interface RProductReviewScoreCard extends RSurrogateJpaRepository<ProductReviewScoreCard,String> {
    public ProductReviewScoreCard findByOwningUsername(String username);
}
