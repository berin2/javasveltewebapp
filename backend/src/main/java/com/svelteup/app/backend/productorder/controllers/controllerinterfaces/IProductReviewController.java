package com.svelteup.app.backend.productorder.controllers.controllerinterfaces;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.utils.controllers.interfaces.HttpController;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PutProductReviewDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;

public interface IProductReviewController  extends HttpController<PostProductReviewDto, PutProductReviewDto, UuidDto> {
    @Override
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(ApplicationApi.PRODUCT_REVIEW)
    void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PostProductReviewDto postProductReviewDto) throws Http405Exception, NotSupportedException;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(ApplicationApi.PRODUCT_REVIEW)
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PutProductReviewDto putProductReviewDto) throws Http400Exception, NotSupportedException;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(ApplicationApi.PRODUCT_REVIEW)
    void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser,@RequestBody UuidDto uuidDto) throws Http405Exception, NotSupportedException;
}
