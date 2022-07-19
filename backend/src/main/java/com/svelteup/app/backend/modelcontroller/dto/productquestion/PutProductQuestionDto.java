package com.svelteup.app.backend.modelcontroller.dto.productquestion;

import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.utils.dateutils.StaticDateService;
import io.swagger.annotations.ApiModel;

import java.sql.Date;
import java.util.UUID;

@ApiModel(value = "Represents the JSON dto used to transmit ProductQuestion Informatation from front end  to backl end.")
public class PutProductQuestionDto extends PostProductQuestionDto{
    public UUID productQuestionId;
    public String productQuestionAnswer;
    public String productQuestionDate;

    public PutProductQuestionDto(ProductQuestion productQuestion)
    {
        super(productQuestion);
        this.productQuestionId = productQuestion.getSurrogateId();
        this.productQuestionAnswer = productQuestion.getProductQuestionAnswer();
        this.productQuestionDate =  StaticDateService.getDateString(productQuestion.getProductQuestionDate());
    }
}
