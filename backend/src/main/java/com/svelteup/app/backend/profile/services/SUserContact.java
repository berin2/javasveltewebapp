package com.svelteup.app.backend.profile.services;

import com.svelteup.app.backend.aop.aspects.paireduser.PPairedOwningUserNonPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.profile.dtos.ContactDto;
import com.svelteup.app.backend.profile.models.BlockedContact;
import com.svelteup.app.backend.profile.models.Contact;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.repositories.RBlockedContact;
import com.svelteup.app.backend.profile.repositories.RContact;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import org.springframework.stereotype.Service;
import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SUserContact extends SHttpExceptionThrower {
    protected final String EMAIL_TAKEN = "%s email has already been taken and verified by a user.";
    protected final SSvelteUpUserProfile svelteUpUserProfileService;
    protected final RContact rContact;
    protected final RBlockedContact rBlockedContact;
    protected final PPairedOwningUserNonPkAccessChecker accessChecker;

    public SUserContact(SSvelteUpUserProfile svelteUpUserProfileService, RContact rContact, RBlockedContact rBlockedContact, PPairedOwningUserNonPkAccessChecker accessChecker) {
        this.svelteUpUserProfileService = svelteUpUserProfileService;
        this.rContact = rContact;
        this.rBlockedContact = rBlockedContact;
        this.accessChecker = accessChecker;
    }


    public SvelteUpUserProfile getUserContactInfo(String authenticatedUser, UUID surrogateId) throws Http403Exception, NotSupportedException
    {
        return this.svelteUpUserProfileService.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
    }

    public List<ContactDto> getUserContacts(String authenticatedUser) throws Http400Exception, Http401Exception, Http403Exception, Http500Exception, NotSupportedException {
        List<Contact> discoveredContacts = rContact.findByOwningUsername(authenticatedUser);
        List<ContactDto> returnContactList = new ArrayList<>();
        ContactDto returnIterator = null;
        for(Contact contact: discoveredContacts)
        {
            this.accessChecker.afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(authenticatedUser,contact);
            returnIterator = contact.toGetDto();
            returnContactList.add(returnIterator);
        }

        return returnContactList;
    }

    public List<ContactDto> getFoeList(String authenticatedUser) throws Http400Exception, Http401Exception, Http403Exception, Http500Exception, NotSupportedException {
        List<BlockedContact> discoveredContacts = rBlockedContact.findByOwningUsername(authenticatedUser);
        List<ContactDto> returnContactList = new ArrayList<>();
        ContactDto returnIterator = null;

        for(BlockedContact contact: discoveredContacts)
        {
            this.accessChecker.afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(authenticatedUser,contact);
            returnIterator = contact.toGetDto();
            returnContactList.add(returnIterator);
        }

        return returnContactList;
    }

    public void addBlockedContact(String authenticatedUser, ContactDto dto) throws NotSupportedException {
        if(this.rContact.existsContactByOwningUsernameAndSecondaryOwningUsername(authenticatedUser,dto.username))
        {
            SvelteUpUserProfile profile = this.svelteUpUserProfileService.findById(dto.username);
            BlockedContact newBlockedContact = new BlockedContact(authenticatedUser,profile.getOwningUsername(),profile.getFirstName());
            rBlockedContact.save(newBlockedContact);
        }
    }
    public void addContact(String authenticatedUser, ContactDto dto) throws NotSupportedException {
        if(this.rContact.existsContactByOwningUsernameAndSecondaryOwningUsername(authenticatedUser,dto.username))
        {
            SvelteUpUserProfile profile = this.svelteUpUserProfileService.findById(dto.username);
            Contact newContact = new Contact(authenticatedUser,profile.getOwningUsername(),profile.getFirstName());
            rContact.save(newContact);
        }
    }

    public void deleteBlockedContact(String authenticatedUser, ContactDto dto) throws NotSupportedException {
        if(this.rBlockedContact.existsContactByOwningUsernameAndSecondaryOwningUsername(authenticatedUser,dto.username))
            this.rBlockedContact.deleteByOwningUsernameAndSecondaryOwningUsername(authenticatedUser,dto.username);

    }
    public void deleteContact(String authenticatedUser, ContactDto dto) throws NotSupportedException {
        if(this.rContact.existsContactByOwningUsernameAndSecondaryOwningUsername(authenticatedUser,dto.username))
            this.rContact.deleteByOwningUsernameAndSecondaryOwningUsername(authenticatedUser,dto.username);
    }


    /**
     * existByEmail checks if an email has already been registered and verified by a user.
     * @param email the email to check existance for.
     * @throws Http400Exception if the email has already taken and verified.
     */
    public void existByEmail(String email) throws Http400Exception
    {
         if(this.svelteUpUserProfileService.existsByEmail(email))
             this.throwHttp404("existByEmail",this.getClass().toString(),"SYSTEM",null,String.format(EMAIL_TAKEN,email));
    }


}
