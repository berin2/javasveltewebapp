package com.svelteup.app.backend.aws.ses.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor()
public class SEmailSender {
    protected final AmazonSimpleEmailService amazonSimpleEmailService;


    protected SendEmailRequest buildEmailRequest(EmailSendEvent event, String thymeLeafProcessedTemplate)
    {
        return new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(event.getEmailDestination()))
                .withMessage(new Message()
                        .withSubject(new Content("SvelteUp Verification Email"))
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8")
                                        .withData(thymeLeafProcessedTemplate)
                                )
                        )
                ).withSource("notifications@svelteup.app");
    }

    public void sendSingleRecipientEmail(EmailSendEvent event, String thymeLeafProcessedTemplate)
    {
        List<String> recipients = new ArrayList<String>();
        recipients.add(event.getEmailDestination());
        SendEmailRequest request = buildEmailRequest(event,thymeLeafProcessedTemplate);
        amazonSimpleEmailService.sendEmail(request);
    }
}
