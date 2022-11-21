package com.svelteup.app.backend.modelcontroller.repositories;

import com.svelteup.app.backend.modelcontroller.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
* ProductRepository manages Product entities.
* */
@Repository
public interface RProduct extends RSurrogateJpaRepository<Product, UUID> {
    public Product findBySurrogateIdAndOwningUsername(UUID id, String username);
    public void deleteProductBySurrogateIdAndOwningUsername(UUID id, String username);
    public List<Product> findAllByOwningUsernameAndActiveTrue(String username);
}
