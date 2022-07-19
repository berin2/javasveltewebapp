package com.svelteup.app.backend.modelcontroller.dto.product;

import com.svelteup.app.backend.modelcontroller.models.Product;
import io.swagger.annotations.ApiModel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@ApiModel(description="Represents a product json used for updates to a Product.")
@NoArgsConstructor
public class PutProductDto extends PostProductDto {
    public UUID surrogateId;

    public PutProductDto(Product productEntity, String [] productImages)
    {
        this.surrogateId = productEntity.getSurrogateId();
        this.productName = productEntity.getProductName();
        this.productAcceptsReturns = productEntity.getProductAcceptsReturns();
        this.productDescription = productEntity.getProductDescription();
        this.productCalories = productEntity.getProductCaloriesCount();
        this.productCost = productEntity.getProductCost();
        this.productImageStrings = productImages;
    }
}
