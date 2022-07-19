package com.svelteup.app.backend.modelcontroller.services.abstractions;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import org.springframework.http.ResponseEntity;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.UUID;

public interface HttpUsernameService <UpdateDTO,CreateDTO> {
    void post(String authenticatedUser, CreateDTO create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException;

    ResponseEntity<UpdateDTO> get(String username) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException, IOException;

    void put(String authenticated_user, UpdateDTO update_DTO) throws Http400Exception, Http401Exception, Http403Exception,Http405Exception, NotSupportedException;

    void delete(String username) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException;
}