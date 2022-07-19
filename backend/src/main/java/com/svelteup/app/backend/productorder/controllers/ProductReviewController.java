package com.svelteup.app.backend.productorder.controllers;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.productorder.controllers.controllerinterfaces.IProductReviewController;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PutProductReviewDto;
import com.svelteup.app.backend.productorder.services.SProductReview;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductReviewController implements IProductReviewController {
    private SProductReview  productReviewService;

    @Override
    public void post(SvelteUpUser authenticatedUser, PostProductReviewDto postProductReviewDto) throws Http405Exception, NotSupportedException {
        productReviewService.post(authenticatedUser.getUsername(),postProductReviewDto);
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, PutProductReviewDto putProductReviewDto) throws Http400Exception, NotSupportedException {
        productReviewService.put(authenticatedUser.getUsername(), putProductReviewDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, UuidDto uuidDto) throws Http405Exception, NotSupportedException {
        productReviewService.delete(authenticatedUser.getUsername(),uuidDto.id);
    }

    @GetMapping(ApplicationApi.PRODUCT_REVIEW)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PutProductReviewDto>> getReviewsByProduct(@RequestBody UuidDto productIdDto)
    {
        List<PutProductReviewDto> productReviewList = productReviewService.getAllForProduct(productIdDto.id);
        return ResponseEntity.ok(productReviewList);
    }
}
