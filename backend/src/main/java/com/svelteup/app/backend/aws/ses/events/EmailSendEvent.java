package com.svelteup.app.backend.aws.ses.events;

import com.svelteup.app.backend.aws.ses.enums.EmailTemplateEnum;
import com.svelteup.app.backend.utils.events.OwningUserEvent;
import com.svelteup.app.backend.utils.events.OwningUserThymeEvent;
import lombok.Data;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

/**
 * EmaiLSendEvent is used to trigger an email sending event. The type of email to
 * send to a user. ThymeLeaf template values are stored in
 */
public class EmailSendEvent extends OwningUserEvent {
    @Getter protected Map<String,Object> thymeLeafContextValues;
    @Getter String emailDestination;
    @Getter EmailTemplateEnum emailTypeEnum;

    public EmailSendEvent(Object source, String owningUsername, Map<String,Object> thymeLeafValues,String emailDestination,EmailTemplateEnum emailEnum)
    {
        super(source,owningUsername);
        this.thymeLeafContextValues = new TreeMap<>();
        this.emailDestination  = emailDestination;
        this.thymeLeafContextValues = thymeLeafValues;
        this.emailTypeEnum  =  emailEnum;
    }
}
