package com.svelteup.app.backend.aws.ses.events;

import com.svelteup.app.backend.aws.ses.services.SEmailSender;
import com.svelteup.app.backend.aws.ses.services.STemplate;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode()
public class EmailSendEventListener implements ApplicationListener<EmailSendEvent> {

    protected SEmailSender emailSenderService;
    protected STemplate templateService;

    public EmailSendEventListener(SEmailSender emailSenderService,STemplate templateService)
    {
        this.emailSenderService = emailSenderService;
        this.templateService = templateService;
    }

    @Override
    public void onApplicationEvent(EmailSendEvent event) {
        String thymeleafProcessedTemplate = this.templateService.generateTemplate(event);
        this.emailSenderService.sendSingleRecipientEmail(event,thymeleafProcessedTemplate);
    }
}
