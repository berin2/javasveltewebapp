package com.svelteup.app.backend.notificationmessage.aop.aspects;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http500Exception;
import com.svelteup.app.backend.notificationmessage.models.message.MessageChat;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MessageChatAspect {

}
