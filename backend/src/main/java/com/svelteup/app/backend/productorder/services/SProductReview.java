package com.svelteup.app.backend.productorder.services;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.OwningUserNonPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUuidService;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserNonPk;
import com.svelteup.app.backend.productorder.dto.ProductReview.PostProductReviewDto;
import com.svelteup.app.backend.productorder.dto.ProductReview.PutProductReviewDto;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import com.svelteup.app.backend.productorder.models.ProductReview;
import com.svelteup.app.backend.productorder.repositories.RProductReview;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SProductReview extends SSurrogateEntityOwningUserNonPk<Long,ProductReview> implements HttpUuidService<PutProductReviewDto, PostProductReviewDto>{
    private RProductReview productReviewRepository;
    private SProductOrder productOrderService;
    private SProductReviewScoreCard productReviewScoreCardService;

    public SProductReview(RProductReview injectedProductReviewRepository, SProductOrder productOrderService) {
        super(injectedProductReviewRepository);
        this.productReviewRepository = injectedProductReviewRepository;
        this.productOrderService = productOrderService;
    }


    @Override
    public void post(String authenticatedUser, PostProductReviewDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        UUID productOrderId = create_DTO.owningProductOrder;
        ProductOrder discoveredOrder = this.productOrderService.findBySurrogateId(productOrderId);
        SvelteUpUser authenticatedUserObject = (SvelteUpUser) SecurityContextHolder.getContext().getAuthentication();

        //ensure user is buyer
        this.pPairedOwningUserNonPkAccessChecker.afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(authenticatedUser,discoveredOrder);

        ProductReview usersSubmittedProductReview = new ProductReview(authenticatedUserObject, create_DTO, discoveredOrder,discoveredOrder.getProductOrderProduct());
        this.productReviewRepository.save(usersSubmittedProductReview);
    }

    @Override
    public ResponseEntity<PutProductReviewDto> get(UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception {
        this.throwHttp405("get", this.getClass().toString(), null);
        return null;
    }

    public List<PutProductReviewDto> getAllForProduct(UUID surrogateProductId) {
        List<ProductReview> productReviewList = this.productReviewRepository.findProductReviewsByProductOrder_SurrogateProductId(surrogateProductId);
        List<PutProductReviewDto> productReviewDtoList = new ArrayList<>();
        PutProductReviewDto reviewDto = null;
        for (ProductReview reviewIterator : productReviewList) {
            reviewDto = reviewIterator.toExistingDto();
            productReviewDtoList.add(reviewDto);
        }

        return productReviewDtoList;
    }

    @Override
    public void put(String authenticated_user, PutProductReviewDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        ProductReview discoveredReview = this.findBySurrogateIdWithCheck(authenticated_user, update_DTO.productReviewId);

        this.productReviewScoreCardService.put(authenticated_user, discoveredReview.getProductReviewStars(), update_DTO.productReviewStars);
        discoveredReview.update(update_DTO);
        this.productReviewRepository.save(discoveredReview);
    }

    @Override
    public void delete(String username, UUID secondary_id) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException {
        ProductReview reviewToDelete = this.findBySurrogateIdWithCheck(username, secondary_id);
        this.productReviewRepository.delete(reviewToDelete);
    }
}


