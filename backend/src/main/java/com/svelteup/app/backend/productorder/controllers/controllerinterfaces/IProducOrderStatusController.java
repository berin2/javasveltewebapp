package com.svelteup.app.backend.productorder.controllers.controllerinterfaces;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.productorder.dto.ProductReview.PutProductReviewDto;
import com.svelteup.app.backend.productorder.dto.PutProductOrderStatusDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.OperationNotSupportedException;
import javax.transaction.NotSupportedException;

public interface IProducOrderStatusController {
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(ApplicationApi.PRODUCT_REVIEW)
    void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PutProductOrderStatusDto putProductReviewDto) throws Http400Exception, NotSupportedException, OperationNotSupportedException;
}
