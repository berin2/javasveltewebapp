package com.svelteup.app.backend.productorder.dto;

import com.svelteup.app.backend.modelcontroller.dto.ingredients.IngredientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApiModel(description = "Represents a JSON used to create a ProductOrder.")
@NoArgsConstructor
public class PostProductOrderDto {
    @ApiModelProperty(value="The  quanitity of the product to order.")
    public Integer productOrderQuantity;
    @ApiModelProperty(value = "The id of the Product to order.")
    public UUID productId;
}
