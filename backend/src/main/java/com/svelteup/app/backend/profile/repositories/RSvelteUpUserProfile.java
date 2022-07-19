package com.svelteup.app.backend.profile.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface RSvelteUpUserProfile  extends RSurrogateJpaRepository<SvelteUpUserProfile,String> {
    boolean existsByEmail(String email);
}
