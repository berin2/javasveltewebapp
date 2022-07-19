package com.svelteup.app.backend.modelcontroller.controllers.implementations;

import com.svelteup.app.backend.modelcontroller.controllers.interfaces.IProductController;
import com.svelteup.app.backend.modelcontroller.dto.UuidDto;
import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.dto.product.PutProductDto;
import com.svelteup.app.backend.modelcontroller.services.services.SProduct;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController() @AllArgsConstructor
public class ProductController implements IProductController {

    SProduct productService;

    @Override
    public ResponseEntity<List<PutProductDto>> getAllProductsByUser(String username, String ownerusername) throws NotSupportedException, IOException {
        ResponseEntity responseList = productService.findAllByUsername(ownerusername);
        return responseList;
    }

    @Override
    public void post(SvelteUpUser authenticatedUser, PostProductDto productDto) throws IOException, NotSupportedException, InterruptedException {
        productService.post(authenticatedUser.getUsername(),  productDto);
    }

    @Override
    public void delete(SvelteUpUser authenticatedUser, UuidDto dtoToDelete) throws NotSupportedException {
        UUID id = dtoToDelete.id;
        productService.delete(authenticatedUser.getUsername(), id);
    }

    @Override
    public void put(SvelteUpUser authenticatedUser, PutProductDto dto) throws NotSupportedException, IOException, InterruptedException {
        productService.put(authenticatedUser.getUsername(),dto);
    }
}
