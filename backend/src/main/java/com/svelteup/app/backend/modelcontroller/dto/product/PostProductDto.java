package com.svelteup.app.backend.modelcontroller.dto.product;

import com.svelteup.app.backend.modelcontroller.models.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@ApiModel(description = "Represents a newly created product json.")
public class PostProductDto {
    public static Integer MAX_PRODUCT_IMAGES = 6;
    public String productName;
    public String productDescription;
    public Integer productCalories;
    public Boolean productAcceptsReturns;
    public Double productCost;
    public String [] productImageStrings;

    public  PostProductDto()
    {
        productImageStrings = new String[MAX_PRODUCT_IMAGES];

    }

}
