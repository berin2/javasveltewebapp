package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.testing.abstractions.ImageStringBank;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Profile("dev")
public class SProductSetupOwningUser extends AOwningUserDataSetup {

    @Autowired RProduct productRepository;
    @Autowired SImageS3 sImageS3;


    @Override
    public void setupEntity(OwningUserDataSetupDto dataSetupDto) throws IOException, NotSupportedException, InterruptedException {


        for(int i = 0; i <dataSetupDto.getRandomIntegerWithinRange(); i++ )
        {
            PostProductDto postProductDto  = new PostProductDto();
            postProductDto.productName  =  this.fakeItTillYouMakeIt.cat().breed() + " Java";
            postProductDto.productCost  = this.fakeItTillYouMakeIt.number().randomDouble(2,1,15);
            postProductDto.productAcceptsReturns = this.fakeItTillYouMakeIt.bool().bool();
            postProductDto.productCalories = this.fakeItTillYouMakeIt.number().randomDigitNotZero() %  600;
            postProductDto.productDescription = "This is a test description with primord  "  +  this.fakeItTillYouMakeIt.ancient().primordial() + ".";
            Product newProduct  = new Product(dataSetupDto.owningUser.getUsername(),postProductDto);
            newProduct = this.productRepository.save(newProduct);
            List<String> productImageList =  new ArrayList<>();
            productImageList.add(ImageStringBank.productImage);

            sImageS3.put(dataSetupDto.owningUser.getUsername(),productImageList,newProduct.getSurrogateId(),Product.class);
            dataSetupDto.productList.add(newProduct);
        }

    }
}
