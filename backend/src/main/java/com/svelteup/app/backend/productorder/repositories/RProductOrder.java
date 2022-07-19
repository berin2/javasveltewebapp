package com.svelteup.app.backend.productorder.repositories;

import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RProductOrder extends RSurrogateJpaRepository<ProductOrder, Long> {

    public List<ProductOrder> findAllByOwningUsername(String username);
}
