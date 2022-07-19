package com.svelteup.app.backend.productorder.dto;

import com.svelteup.app.backend.productorder.models.ApplicationNotificationEnums;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

import java.util.UUID;
@ApiModel(description = "Represents the class used to change ProductOrderStatus ")
@NoArgsConstructor
public class PutProductOrderStatusDto {
    @ApiModelProperty(value = "The id of the product order to change status for.")
    public UUID productOrderId;
    @ApiModelProperty(value = "The new string status to change the productOrderStatus for.")
    public ApplicationNotificationEnums productOrderStatusCode;

    public PutProductOrderStatusDto(UUID productOrderId, ApplicationNotificationEnums productOrderStatus)
    {
        this.productOrderId =productOrderId;
        this.productOrderStatusCode = productOrderStatus;
    }
}
