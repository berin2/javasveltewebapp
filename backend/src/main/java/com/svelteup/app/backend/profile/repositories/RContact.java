package com.svelteup.app.backend.profile.repositories;

import com.svelteup.app.backend.profile.models.Contact;
import org.springframework.stereotype.Repository;

@Repository()
public interface RContact extends ContactOperationsRepository<Contact> {
}
