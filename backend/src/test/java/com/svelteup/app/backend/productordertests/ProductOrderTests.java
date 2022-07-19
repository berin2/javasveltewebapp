package com.svelteup.app.backend.productordertests;

import com.svelteup.app.backend.BaseProductClass;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.productorder.dto.PostProductOrderDto;
import com.svelteup.app.backend.productorder.services.SProductOrder;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.NotSupportedException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductOrderTests extends BaseProductClass  {

    SProductOrder productOrderService;

    @Autowired
    public ProductOrderTests(RSvelteUpUser repository, RProduct productRepository, SProductOrder prodOrderService) {
        super(repository, productRepository);
        this.productOrderService = prodOrderService;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    @Test
    public void createProductOrderTest() throws NotSupportedException
    {
        PostProductOrderDto productOrderDto = new PostProductOrderDto();
        productOrderDto.productOrderQuantity = 1;
        productOrderDto.productId = this.createdProduct.getSurrogateId();
        this.productOrderService.post(this.testUserOne.getUsername(),productOrderDto);
    }
}
