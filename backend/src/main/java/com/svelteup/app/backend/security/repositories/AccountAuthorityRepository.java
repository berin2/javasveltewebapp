package com.svelteup.app.backend.security.repositories;

import com.svelteup.app.backend.security.models.AccountAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/*
* AccountAuthorityRepository is responsible for managing AccountAuthority entities.
* */
@Repository
public interface AccountAuthorityRepository extends JpaRepository<AccountAuthority, UUID> {
}
