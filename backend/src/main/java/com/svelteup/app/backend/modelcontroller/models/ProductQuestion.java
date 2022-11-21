package com.svelteup.app.backend.modelcontroller.models;

import com.svelteup.app.backend.modelcontroller.dto.productquestion.PutProductQuestionDto;
import com.svelteup.app.backend.modelcontroller.models.usermodels.PairedUserNonPrimaryKeyEntity;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.utils.dateutils.StaticDateService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity()
@Data()
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductQuestion extends PairedUserNonPrimaryKeyEntity {
    @Id()
    @GeneratedValue()
    Long productQuestionId;
    String productQuestion;
    String productQuestionAnswer;
    Date productQuestionDate;
    UUID owningProductSurrogateId;

    public ProductQuestion(String authenticatedQuestionAsker,Product productToQuestion, String productQuestion)
    {
        super(productToQuestion.getOwningUsername(),authenticatedQuestionAsker);
        this.productQuestionDate = StaticDateService.getNow();
        this.productQuestion = productQuestion;
        this.owningProductSurrogateId = productToQuestion.getSurrogateId();

        this.productQuestionAnswer = null;
    }

    /**
     * @return PutProductQuestionDto of this ProductQuestion.
     */
    public PutProductQuestionDto toExistingDto() {
        return new PutProductQuestionDto(this);
    }
}
