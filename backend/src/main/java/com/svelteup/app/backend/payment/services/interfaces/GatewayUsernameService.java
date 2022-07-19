package com.svelteup.app.backend.payment.services.interfaces;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import org.springframework.http.ResponseEntity;

import javax.transaction.NotSupportedException;

public interface GatewayUsernameService<GetMethodDto,PostMethodDto,PutMethodDto,DeleteDto> {
    ResponseEntity<GetMethodDto> get(String username) throws NotSupportedException, Http400Exception,Http405Exception;
    void put(String username, PutMethodDto postDto) throws NotSupportedException, Http400Exception,Http405Exception;;
    void post(String username, PostMethodDto postDto) throws NotSupportedException, Http400Exception,Http405Exception;;
    void delete(String username,DeleteDto deleteDto) throws NotSupportedException, Http400Exception,Http405Exception;
}
