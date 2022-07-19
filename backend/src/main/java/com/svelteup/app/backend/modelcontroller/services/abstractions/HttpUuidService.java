package com.svelteup.app.backend.modelcontroller.services.abstractions;

/*
* HttpUuidService is an interface that supports CRUD operations through exposing public
* methods which correspond to POST,GET,PUT,and DELETE methods.
* */

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;

import org.springframework.http.ResponseEntity;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.UUID;


public interface HttpUuidService<UpdateDTO,CreateDTO> {
    public void post(String authenticatedUser, CreateDTO create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException, IOException, InterruptedException;

    public ResponseEntity<UpdateDTO> get(UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException;

    public void put(String authenticated_user, UpdateDTO update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException, IOException, InterruptedException;

    public void delete(String username,UUID secondary_id) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException;
}