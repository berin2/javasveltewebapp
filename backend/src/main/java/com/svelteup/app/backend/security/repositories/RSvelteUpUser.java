package com.svelteup.app.backend.security.repositories;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
* AccountDetailsRepository is responsible for mjanaging SvelteUpUser.
* */
@Repository
public interface RSvelteUpUser extends JpaRepository<SvelteUpUser,String> {
}
