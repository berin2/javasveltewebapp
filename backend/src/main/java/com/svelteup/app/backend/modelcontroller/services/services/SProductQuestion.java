package com.svelteup.app.backend.modelcontroller.services.services;

import com.svelteup.app.backend.aop.aspects.paireduser.OwningUserPairedNonPkEntityAccessCheck;
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
import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
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
        implements HttpUuidService<PutProductQuestionDto, PostProductQuestionDto>,
        OwningUserPairedNonPkEntityAccessCheck<ProductQuestion>
{
    protected RProductQuestion rProductQuestion;
    protected SSurrogateEntity<Long,Product> sProduct;
    protected Sort productQuestionSort;
    protected Pageable productQuestionPageable;
    protected final Integer PAGE_SIZE = 5;

    public SProductQuestion(RProductQuestion surrogateJpaRepository, RProduct rProduct) {
        super(surrogateJpaRepository);
        this.rProductQuestion =  surrogateJpaRepository;
        this.productQuestionSort = Sort.by("pageRequestDate").descending();
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
        Page<ProductQuestion> productQuestionPage = this.rProductQuestion.findProductQuestionsByOwningProduct_SurrogateId(pageableRequest,surrogateId);
        List<PutProductQuestionDto> productQuestionDtoList = new ArrayList<>();

        for(ProductQuestion productQuestion:productQuestionPage)
            productQuestionDtoList.add(productQuestion.toExistingDto());

        return ResponseEntity.ok(productQuestionDtoList);
    }

    @Override
    public void put(String authenticated_user, PutProductQuestionDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, Http405Exception, NotSupportedException {
        ProductQuestion questionToUpdate = this.afterReturningIsOwningUserOrSecondaryUserCheck(authenticated_user,update_DTO.productQuestionId);

        if(questionToUpdate.getOwningUsername().equals(authenticated_user))
            questionToUpdate.setProductQuestionAnswer(update_DTO.productQuestionAnswer);
        else
            questionToUpdate.setProductQuestion(update_DTO.productQuestion);
    }

    @Override
    public void delete(String username, UUID secondary_id) throws Http400Exception, Http401Exception, Http405Exception, NotSupportedException {
        ProductQuestion questionToDelete = this.afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(username,secondary_id);
        this.rProductQuestion.delete(questionToDelete);
    }

    @Override
    public ProductQuestion afterReturningOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, UUID entitySurrogateId) throws Http403Exception, NotSupportedException {
        return this.findBySurrogateId(entitySurrogateId);
    }

    /**
     * afterReturningIsOwningUserCheck is used to identify if the user is the owning or secondary owning user.
     *
     * @param authenticatedUser The user to check against the entity.
     * @param entityId          The entity passed to the calling method.
     * @return a true Boolean indicating that the authenticatedUser is the owningUser, or false if the user
     * is the secondary user. Before calling this method, it's important to ensure the user is either the owningUser
     * or the secondaryOwningUser.
     */
    @Override
    public ProductQuestion afterReturningIsOwningUserOrSecondaryUserCheck(String authenticatedUser, UUID entityId) throws NotSupportedException {
        return this.findBySurrogateId(entityId);
    }

    @Override
    public ProductQuestion beforeOwningUserPairedNonPrimaryKeyPermissionCheck(String authenticatedUser, ProductQuestion entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    @Override
    public ProductQuestion beforeOwningUserPairedNonPrimaryKeyIsOwningUserCheck(String authenticatedUser, ProductQuestion entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    @Override
    public ProductQuestion beforeOwningUserPairedNonPrimaryKeyIsSecondaryOwningUserCheck(String authenticatedUser, ProductQuestion entity) throws Http403Exception, NotSupportedException {
        return entity;
    }

    /**
     * beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck ensures a user is neither the primary or secondary owning user.
     *
     * @param authenticatedUser the user to permission check.
     * @param entity            the user to check permissions for.
     * @return entity the entity passed as a parameter.
     * @throws Http403Exception      if the user is not listed as secondaryUser and primaryOwningUser.
     * @throws NotSupportedException if the method call is not supported in implementing service
     */
    @Override
    public ProductQuestion beforeOwningUserPairedNonPrimaryKeyIsNotPrimaryAndSecondaryOwningUserCheck(String authenticatedUser, ProductQuestion entity) throws Http403Exception, NotSupportedException {
        return entity;
    }
}
