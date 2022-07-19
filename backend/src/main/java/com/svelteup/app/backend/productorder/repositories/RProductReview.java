package com.svelteup.app.backend.productorder.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.productorder.models.ProductReview;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RProductReview  extends RSurrogateJpaRepository<ProductReview, Long> {
    public List<ProductReview> findProductReviewsByOwningProductOrder_ProductOrderProduct_SurrogateId(UUID id);
}
