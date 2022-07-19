package com.svelteup.app.backend.productorder.dto;

import com.svelteup.app.backend.modelcontroller.dto.product.PutProductDto;
import com.svelteup.app.backend.productorder.models.ProductOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import java.util.UUID;

@ApiModel(description = "Represents an JSON a user can use to view an order..")
@NoArgsConstructor
public class PutProductOrderDto extends PostProductOrderDto{
    @ApiModelProperty(value="represents the surrogate UUID of the product.")
    public UUID productOrderId;
    @ApiModelProperty(value = "represents the ordered product. ")
    public PutProductDto productOrderProduct;
    @ApiModelProperty(value = "Represents the cost of the product order.")
    public double productOrderCost;
    public PutProductOrderStatusDto productOrderStatus;

    public PutProductOrderDto(ProductOrder productOrder)
    {
        super();
        this.productOrderId = productOrder.getSurrogateId();
        this.productId = productOrder.getProductOrderProduct().getSurrogateId();
        this.productOrderStatus = new PutProductOrderStatusDto(productOrder.getSurrogateId(),productOrder.getProductOrderStatus());
        this.productOrderCost = productOrder.getProductOrderCost();
        this.productOrderQuantity = productOrder.getProductOrderQuantity();
        this.productOrderProduct = new PutProductDto(productOrder.getProductOrderProduct(),null);
    }
}
