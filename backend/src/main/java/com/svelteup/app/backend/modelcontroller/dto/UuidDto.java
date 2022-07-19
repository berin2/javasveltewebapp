package com.svelteup.app.backend.modelcontroller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

@ApiModel(description = "used to transmit uuid information from the fronend to the backend. Used in delete operation of anythign uuid based.")
public class UuidDto {
    @ApiModelProperty(value = "The uuid of the item to transmit  information for.")
    public UUID id;
}
