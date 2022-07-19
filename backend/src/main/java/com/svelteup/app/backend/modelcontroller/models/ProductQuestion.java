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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productId",referencedColumnName = "productId")
    Product owningProduct;

    public ProductQuestion(String authenticatedQuestionAsker,Product productToQuestion, String productQuestion)
    {
        super(productToQuestion.getOwningUsername(),authenticatedQuestionAsker);
        this.productQuestionDate = StaticDateService.getNow();
        this.productQuestion = productQuestion;
        this.owningProduct = productToQuestion;

        this.productQuestionAnswer = null;
    }

    /**
     * @return PutProductQuestionDto of this ProductQuestion.
     */
    public PutProductQuestionDto toExistingDto() {
        return new PutProductQuestionDto(this);
    }
}
