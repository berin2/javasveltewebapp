package com.svelteup.app.backend.modelcontroller.controllers.implementations;

import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http405Exception;
import com.svelteup.app.backend.modelcontroller.controllers.interfaces.IProductQuestionController;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.modelcontroller.dto.productquestion.PostProductQuestionDto;
import com.svelteup.app.backend.modelcontroller.dto.productquestion.PutProductQuestionDto;
import com.svelteup.app.backend.modelcontroller.services.services.SProductQuestion;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@RestController()
@AllArgsConstructor() @NoArgsConstructor()
@EqualsAndHashCode()
public class ProductQuestionController implements IProductQuestionController {

    SProductQuestion sProductQuestion;

    @Override
    public void post(SvelteUpUser authenticatedUser, PostProductQuestionDto postProductQuestionDto) throws Http405Exception, NotSupportedException {
        this.sProductQuestion
                .post(authenticatedUser.getUsername(),postProductQuestionDto);
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, PutProductQuestionDto putProductQuestionDto) throws Http400Exception, NotSupportedException {
        this.sProductQuestion
                .put(authenticatedUser.getUsername(),putProductQuestionDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, UuidDto uuidDto) throws Http405Exception, NotSupportedException {
        this.sProductQuestion
                .delete(authenticatedUser.getUsername(),uuidDto.id);
    }

    @Override
    public void get(SvelteUpUser authenticatedUser, Integer pageIndex, UUID productId) {
        this.sProductQuestion
                .getPage(pageIndex, productId);
    }
}
