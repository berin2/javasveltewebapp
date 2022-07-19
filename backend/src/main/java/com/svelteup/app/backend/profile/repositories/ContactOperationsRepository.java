package com.svelteup.app.backend.profile.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.profile.models.BlockedContact;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ContactOperationsRepository<T> extends RSurrogateJpaRepository<T,Long> {
    List<T> findByOwningUsername(String owningUsername);
    boolean existsContactByOwningUsernameAndSecondaryOwningUsername(String authenticatedUser,String secondaryUser);
    T findContactByOwningUsernameAndSecondaryOwningUsername(String authenticatedUser, String secondaryUser);
    boolean deleteByOwningUsernameAndSecondaryOwningUsername(String owningUsername,String secondaryOwningUsername);

}
