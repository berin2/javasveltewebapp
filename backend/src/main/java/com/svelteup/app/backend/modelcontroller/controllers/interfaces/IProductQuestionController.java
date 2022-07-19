package com.svelteup.app.backend.modelcontroller.controllers.interfaces;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.modelcontroller.dto.productquestion.PostProductQuestionDto;
import com.svelteup.app.backend.modelcontroller.dto.productquestion.PutProductQuestionDto;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.util.UUID;

public interface IProductQuestionController extends HttpController<PostProductQuestionDto, PutProductQuestionDto, UuidDto> {
    @Override
    @PostMapping(ApplicationApi.PRODUCT_QUESTION)
    @ResponseStatus(HttpStatus.OK)
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PostProductQuestionDto postProductOrderDto) throws NotSupportedException;

    @Override
    @PutMapping(ApplicationApi.PRODUCT_QUESTION)
    @ResponseStatus(HttpStatus.OK)
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser,@RequestBody PutProductQuestionDto putProductOrderStatusDto) throws NotSupportedException;

    @Override
    @DeleteMapping(ApplicationApi.PRODUCT_QUESTION)
    @ResponseStatus(HttpStatus.OK)
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody UuidDto uuidDto) throws NotSupportedException;

    @GetMapping(ApplicationApi.GET_PRODUCT_QUESTION_PAGE)
    @ResponseStatus(HttpStatus.OK)
    void get(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @PathVariable Integer pageIndex, @PathVariable UUID productId);
}
