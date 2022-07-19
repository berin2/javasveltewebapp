package com.svelteup.app.backend.security.repositories;

import com.svelteup.app.backend.security.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Authority findAuthorityByAuthority(String authority);
}
