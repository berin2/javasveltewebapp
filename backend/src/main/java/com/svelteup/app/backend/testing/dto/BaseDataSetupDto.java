package com.svelteup.app.backend.testing.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@ApiModel("represents base class and shared functionality all data setup classes need to inherit from.")
@AllArgsConstructor
public class BaseDataSetupDto {
    public Integer minimum;
    public Integer maximum;

    public Integer  getRandomIntegerWithinRange(){
        return ThreadLocalRandom.current().nextInt(this.minimum,  this.maximum);
    }
}
