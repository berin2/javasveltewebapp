package com.svelteup.app.backend.modelcontroller.controllers.interfaces;

import com.svelteup.app.backend.api.ApplicationApi;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.dto.product.PutProductDto;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;

/*
* IProductController defines all operations users can perform on Product HostDescriptor.
* */
public interface IProductController {
    @GetMapping(ApplicationApi.GET_ALL_PRODUCTS)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PutProductDto>> getAllProductsByUser(@AuthenticationPrincipal String username, @PathVariable String ownerusername) throws NotSupportedException, IOException;
    @PostMapping(ApplicationApi.PRODUCT)
    @ResponseStatus(HttpStatus.OK)
    public void post(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PostProductDto productDto) throws IOException, NotSupportedException, InterruptedException;
    @DeleteMapping(ApplicationApi.PRODUCT)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody UuidDto dtoToDelete) throws NotSupportedException;
    @PutMapping(ApplicationApi.PRODUCT)
    @ResponseStatus(HttpStatus.OK)
    public void put(@AuthenticationPrincipal SvelteUpUser authenticatedUser, @RequestBody PutProductDto dto) throws NotSupportedException, IOException, InterruptedException;
}
