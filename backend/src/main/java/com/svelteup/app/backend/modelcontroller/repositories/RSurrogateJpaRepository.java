package com.svelteup.app.backend.modelcontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

/*
* RSurrogateJpaRepository is an extension of the JPA repository that allows for querying items
* based on the surrogate id, which in most cases should be UUID.
* */
@NoRepositoryBean
public interface RSurrogateJpaRepository<T,ID> extends JpaRepository<T,ID> {
    public Optional<T> findBySurrogateId(UUID surrogate_id);
    public void deleteBySurrogateId(UUID surrogate_id);
}
