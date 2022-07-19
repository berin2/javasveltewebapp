package com.svelteup.app.backend;

import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import org.springframework.beans.factory.InitializingBean;

public class BaseProductClass extends BaseTestClass implements InitializingBean {
    protected  RProduct productRepository;
    protected Product createdProduct;
    public BaseProductClass(RSvelteUpUser repository, RProduct productRepository) {
        super(repository);
        this.productRepository = productRepository;
        this.createdProduct = null;
    }


    @Override
    public void afterPropertiesSet() throws Exception
    {
        super.afterPropertiesSet();
        PostProductDto postDto = new PostProductDto();
        postDto.productName = "New product";
        postDto.productDescription  = "New product test description.";
        postDto.productCost = 100.00;
        postDto.productAcceptsReturns = false;
        postDto.productCalories = 114;

        Product newProduct = new Product(this.testUserOne.getUsername(),postDto);
        this.createdProduct = this.productRepository.saveAndFlush(newProduct);
    }

}
