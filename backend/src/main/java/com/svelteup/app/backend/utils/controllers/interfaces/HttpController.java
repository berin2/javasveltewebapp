package com.svelteup.app.backend.utils.controllers.interfaces;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.transaction.NotSupportedException;

public interface HttpController<PostDto,PutDto,DeleteDto> {
    public void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, PostDto postDto) throws Http405Exception,NotSupportedException;
    public void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, PutDto putDto) throws Http400Exception,NotSupportedException;
    public void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, DeleteDto deleteDto) throws Http405Exception, NotSupportedException;
}
