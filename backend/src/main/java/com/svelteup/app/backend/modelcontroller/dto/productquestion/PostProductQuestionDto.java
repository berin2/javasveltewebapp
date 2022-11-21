package com.svelteup.app.backend.modelcontroller.dto.productquestion;

import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.utils.dateutils.StaticDateService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel(value = "PostProductQuestionDto represents JSON used to transfer new Product question by users.")
public class PostProductQuestionDto {
    public String productQuestion;
    public String productQuestionDate;
    public UUID productId;

    public PostProductQuestionDto(ProductQuestion productQuestion)
    {
        this.productId =  productQuestion.getOwningProductSurrogateId();
        this.productQuestion = productQuestion.getProductQuestion();
        this.productQuestionDate  = StaticDateService.getDateString(productQuestion.getProductQuestionDate());
    }
}
