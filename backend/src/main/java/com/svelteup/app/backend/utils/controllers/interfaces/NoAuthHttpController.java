package com.svelteup.app.backend.utils.controllers.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * NoAuthHttpController is used to expose a standard interface for POST http methods.
 * @param <PostDto>
 * @param <PutDto>
 * @param <DeleteDto>
 */
public interface NoAuthHttpController <PostDto,PutDto,DeleteDto>{
    void post(PostDto postDto, HttpServletRequest request, HttpServletResponse response);
}
