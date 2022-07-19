package com.svelteup.app.backend.profile.repositories;

import com.svelteup.app.backend.modelcontroller.repositories.RSurrogateJpaRepository;
import com.svelteup.app.backend.profile.models.BlockedContact;
import com.svelteup.app.backend.profile.models.Contact;
import jdk.nashorn.internal.ir.Block;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RBlockedContact extends ContactOperationsRepository<BlockedContact> {
    List<BlockedContact> findByOwningUsername(String owningUsername);
    boolean existsContactByOwningUsernameAndSecondaryOwningUsername(String authenticatedUser,String secondaryUser);
    BlockedContact findContactByOwningUsernameAndSecondaryOwningUsername(String authenticatedUser, String secondaryUser);

}
