package com.svelteup.app.backend.modelcontroller.controllers.interfaces;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.dtostores.AppInitDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import javax.validation.constraints.NotNull;
import java.io.IOException;

public interface ILogInOutAuthenticateController {
    @GetMapping(value = ApplicationApi.GET_AUTHENTICATE,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    ResponseEntity<AppInitDto> authenticate(@AuthenticationPrincipal SvelteUpUser user) throws OperationNotSupportedException, NotSupportedException, IOException;


    @GetMapping(ApplicationApi.GET_AUTH_ON_MOUNT)
    @ResponseStatus(HttpStatus.OK)
    @Validated()
    ResponseEntity<AppInitDto> onMountAuthenticate(@AuthenticationPrincipal SvelteUpUser user) throws OperationNotSupportedException, NotSupportedException, IOException;

    @PostMapping(value = ApplicationApi.LOGIN,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    ResponseEntity<AppInitDto> login(@AuthenticationPrincipal SvelteUpUser user, @NotNull HttpServletRequest request, @NotNull  HttpServletResponse response) throws OperationNotSupportedException, IOException, NotSupportedException;

    @DeleteMapping(value = ApplicationApi.LOGOUT)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    void logout(@NotNull HttpServletRequest sessionRequest, @NotNull HttpServletResponse httpServletResponse);
}
