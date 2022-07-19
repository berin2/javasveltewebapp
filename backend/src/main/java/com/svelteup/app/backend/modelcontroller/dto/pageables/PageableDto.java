package com.svelteup.app.backend.modelcontroller.dto.pageables;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "PageableDto represents a JSON used to transmit PageRequest paramaeters to the backend.")
public class PageableDto {
    public Integer pageIndex;
}
