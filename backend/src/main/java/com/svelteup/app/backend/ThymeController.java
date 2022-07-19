package com.svelteup.app.backend;

import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.modelcontroller.dto.product.PutProductDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.modelcontroller.services.services.SProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.NotSupportedException;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController()
public class ThymeController  {

    @Autowired
    SImageS3 sImageS3;
    File springfile;
    @Autowired
    SProduct sProduct;
    @Autowired
    RProduct rProduct;
    UUID productUUID;
    @Autowired protected SProduct productService;

    @GetMapping("/testing")
    public @ResponseBody PutProductDto testTypescriptClient() throws IOException {

        List<Product> discoveredProduct = this.rProduct.findAllByOwningUsername("test44");
        Product productToReturn = discoveredProduct.get(0);
        String[] arr = sImageS3.getMultipleImages(Product.class,"test44",Product.MAXIMUM_IMAGES,productToReturn.getSurrogateId());
        //need processing function here
        PutProductDto retDto = productToReturn.toExistingDto();
        retDto.productImageStrings = arr;
        return retDto;
    }

    @PutMapping("/test-two")
    @ResponseStatus(HttpStatus.OK)
    public void testTwo( @RequestBody PutProductDto putProductDto) throws IOException, NotSupportedException, InterruptedException {
        sProduct.put("test44",putProductDto);
    }

    @PostMapping("/test-three")
    @ResponseStatus(HttpStatus.OK)
    public void testThree(@RequestBody PutProductDto dto) throws IOException, NotSupportedException, InterruptedException {
        sProduct.post("test-44",dto);
    }

    @PostConstruct
    public void buildFile() throws IOException, NotSupportedException, InterruptedException {
        Resource springFile = new ClassPathResource("/static/images/sveltelogo.png");
        this.springfile = springFile.getFile();
        PutProductDto testProduct = new PutProductDto();
        testProduct.productCost =71.12;
        testProduct.productName = "Iceberg Jolt DB-Test";
        testProduct.productCalories = 789;
        testProduct.productDescription ="Test";
        testProduct.productAcceptsReturns = true;

        testProduct.productImageStrings[0] = null;
        testProduct.productImageStrings[1] = null;

        this.sProduct.post("test44", testProduct);
    }

   }
