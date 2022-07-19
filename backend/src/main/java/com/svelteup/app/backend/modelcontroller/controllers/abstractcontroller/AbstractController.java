package com.svelteup.app.backend.modelcontroller.controllers.abstractcontroller;

import com.svelteup.app.backend.utils.exceptionutils.SHttpExceptionThrower;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode()
@AllArgsConstructor()
@NoArgsConstructor()
public abstract class AbstractController {
    protected SHttpExceptionThrower exceptionThrowerService;
}
