package com.svelteup.app.backend.profile.models;

import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToGetDto;
import com.svelteup.app.backend.modelcontroller.models.Interfaces.ToPutDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import com.svelteup.app.backend.profile.dtos.ContactDto;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity()
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class Contact extends PairedUserNonPrimaryKeyEntity implements ToGetDto<ContactDto> {
    @Id
    Long contactId;
    String name;


    public Contact(String contactOwner, String secondaryOwningUser, String secondaryContactName)
    {
        super(contactOwner,secondaryOwningUser);
        this.name = secondaryContactName;
    }

    @Override
    public ContactDto toGetDto() {

        ContactDto returnDto = new ContactDto(this.secondaryOwningUsername,this.name,null);
        return returnDto;
    }
}
