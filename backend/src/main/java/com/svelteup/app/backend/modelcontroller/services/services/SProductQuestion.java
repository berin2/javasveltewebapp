package com.svelteup.app.backend.modelcontroller.services.services;

import com.svelteup.app.backend.aop.aspects.paireduser.OwningUserPairedNonPkEntityAccessCheckAOPTarget;
import com.svelteup.app.backend.aop.aspects.paireduser.PPairedOwningUserNonPkAccessChecker;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.dto.productquestion.PostProductQuestionDto;
import com.svelteup.app.backend.modelcontroller.dto.productquestion.PutProductQuestionDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.modelcontroller.repositories.RProductQuestion;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUuidService;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import lombok.EqualsAndHashCode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@EqualsAndHashCode(callSuper = true)
public class SProductQuestion extends SSurrogateEntity<Long, ProductQuestion>
        implements HttpUuidService<PutProductQuestionDto, PostProductQuestionDto>
{
    protected RProductQuestion rProductQuestion;
    protected SSurrogateEntity<Long,Product> sProduct;
    protected Sort productQuestionSort;
    protected Pageable productQuestionPageable;
    protected  PPairedOwningUserNonPkAccessChecker accessChecker;
    protected final Integer PAGE_SIZE = 5;

    public SProductQuestion(RProductQuestion surrogateJpaRepository, PPairedOwningUserNonPkAccessChecker accessChecker) {
        super(surrogateJpaRepository);
        this.rProductQuestion =  surrogateJpaRepository;
        this.productQuestionSort = Sort.by("pageRequestDate").descending();
        this.accessChecker = accessChecker;
    }

    @Override
    public void post(String authenticatedUser, PostProductQuestionDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        Product discoveredProduct = this.sProduct.findBySurrogateId(create_DTO.productId);
        ProductQuestion newProductQuestion = new ProductQuestion(authenticatedUser,discoveredProduct, create_DTO.productQuestion);
        rProductQuestion.save(newProductQuestion);
    }

    @Override
    public ResponseEntity<PutProductQuestionDto> get(UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        throw new NotSupportedException("SProductQuestion does not support get(UUID id) method.");
    }

    public ResponseEntity<List<PutProductQuestionDto>> getPage(Integer pageIndex, UUID surrogateId)
    {
        Pageable  pageableRequest = PageRequest.of(PAGE_SIZE,pageIndex,productQuestionSort);
        Page<ProductQuestion> productQuestionPage = this.rProductQuestion.findProductQuestionsByOwningProductSurrogateId(pageableRequest,surrogateId);
        List<PutProductQuestionDto> productQuestionDtoList = new ArrayList<>();

        for(ProductQuestion productQuestion:productQuestionPage)
            productQuestionDtoList.add(productQuestion.toExistingDto());

        return ResponseEntity.ok(productQuestionDtoList);
    }

    @Override
    public void put(String authenticated_user, PutProductQuestionDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        ProductQuestion questionToUpdate = this.findBySurrogateId(update_DTO.productQuestionId) ;
        this.accessChecker.afterReturningSecondaryOwningUserPairedNonPrimaryKeyPermissionCheck(authenticated_user,questionToUpdate);

        if(questionToUpdate.getOwningUsername().equals(authenticated_user))
            questionToUpdate.setProductQuestionAnswer(update_DTO.productQuestionAnswer);
        else
            questionToUpdate.setProductQuestion(update_DTO.productQuestion);
    }

    @Override
    public void delete(String username, UUID secondary_id) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException {
        ProductQuestion questionToDelete = this.findBySurrogateId(secondary_id);
        this.accessChecker.afterReturningSecondaryOwningUserPairedNonPrimaryKeyPermissionCheck(username, questionToDelete);
        this.rProductQuestion.delete(questionToDelete);
    }
}
