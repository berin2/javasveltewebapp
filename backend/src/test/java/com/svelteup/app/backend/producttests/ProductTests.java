package com.svelteup.app.backend.producttests;

import com.svelteup.app.backend.BaseTestClass;
import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.services.services.SProduct;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.NotSupportedException;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductTests extends BaseTestClass{
    @Autowired
    SProduct productTestService;

    @Autowired
    public ProductTests(RSvelteUpUser repository) {
        super(repository);
    }


    @Test
    public void testCreateProduct() throws IOException, NotSupportedException, InterruptedException {
        PostProductDto newProduct = new PostProductDto();
        newProduct.productName = "test_prod";
        newProduct.productDescription = "test description";
        newProduct.productCalories = 400;
        newProduct.productAcceptsReturns = true;
        this.productTestService.post(this.testUserOne.getUsername(),newProduct);
    }
}
