package com.svelteup.app.backend.profile.models;

import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToGetDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import com.svelteup.app.backend.profile.dtos.ContactDto;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class BlockedContact extends PairedUserNonPrimaryKeyEntity implements ToGetDto<ContactDto> {
    @Id
    Long blockedContactId;
    @Column(nullable = false)
    String name;

    public BlockedContact(String contactOwner,String name, String secondaryUserContact)
    {
        super(contactOwner,secondaryUserContact);
    }

    @Override
    public ContactDto toGetDto() {
        ContactDto blockedContact = new ContactDto(this.secondaryOwningUsername,this.name,null);
        return blockedContact;
    }
}
