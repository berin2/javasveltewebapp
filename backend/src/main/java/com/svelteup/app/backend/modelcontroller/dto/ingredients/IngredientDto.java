package com.svelteup.app.backend.modelcontroller.dto.ingredients;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "IngredientDto is used to transfer data from the frontend to the backend for ProductIngredients.")
public class IngredientDto {
    @ApiModelProperty(name = "The name of the ingredient to put in a users product.")
    public String name;
}
