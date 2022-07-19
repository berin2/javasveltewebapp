package com.svelteup.app.backend.aws.ses.services;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.aws.ses.events.EmailSendEvent;
import com.svelteup.app.backend.utils.objects.HostDescriptor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class STemplate implements ServletContextAware {

    TemplateEngine thymeleafEngine;
    HostDescriptor descriptor;
    ServletContext  servletContext;

    public STemplate(TemplateEngine templateEngine, HostDescriptor descriptor, ServletContext context)
    {
        this.thymeleafEngine = templateEngine;
        this.descriptor = descriptor;
        this.servletContext = context;
    }

    public String generateTemplate(EmailSendEvent emailSendEvent)
    {
        String returnTemplate = null;
        switch(emailSendEvent.getEmailTypeEnum())
        {
            case VERIFICATION_TEMPLATE:
                returnTemplate = this.getVerifyTemplate(emailSendEvent);
                break;
        }

        return  returnTemplate;
    }
    public String getVerifyTemplate(EmailSendEvent emailSendEvent)
    {
        Map<String,Object> verifyTemplateMap = emailSendEvent.getThymeLeafContextValues();

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        WebContext thymeContext = new WebContext(request, response, servletContext);
        thymeContext.setVariable("username",verifyTemplateMap.get("username").toString());
        thymeContext.setVariable("emailToken",descriptor.getHostString() + ApplicationApi.POST_EMAIL_VERIFY + "/"  + emailSendEvent.getThymeLeafContextValues().get("emailToken").toString());
        return thymeleafEngine.process("verify",thymeContext);
    }

    public  String getRedirectTemplate()
    {
        IWebContext thymeContext = new WebContext(null,null,this.servletContext);

        return thymeleafEngine.process("redirect",thymeContext);
    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
