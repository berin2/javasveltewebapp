package com.svelteup.app.backend.modelcontroller.repositories;

import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * RProductQuestion is a JPA repository which manages ProductQuestion entities.
 */
@Repository
public interface RProductQuestion extends RSurrogateJpaRepository<ProductQuestion,Long>{
    Page<ProductQuestion> findProductQuestionsByOwningProductSurrogateId(Pageable pageable,UUID productId);
}
