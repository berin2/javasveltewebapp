package com.svelteup.app.backend.modelcontroller.services.services;

import com.svelteup.app.backend.aop.aspects.owningusernonpk.POwningUserNonPkAccessChecker;
import com.svelteup.app.backend.aws.s3.services.SImageS3;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http400Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http401Exception;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.dto.product.PostProductDto;
import com.svelteup.app.backend.modelcontroller.dto.product.PutProductDto;
import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.repositories.RProduct;
import com.svelteup.app.backend.modelcontroller.services.abstractions.HttpUuidService;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.*;

/*
 * ProductIngredientService is used to manage ProductIngredients.
 */

@Service()
@Scope("prototype")
public class SProduct  extends SSurrogateEntity<UUID,Product> implements HttpUuidService<PutProductDto, PostProductDto>
{

    private static final String CANNOT_PERFORM_OPERATION  = "The user %s does not have permission to %s object with surrogateId %s .";
    private final RProduct productRepository;
    protected  SImageS3 s3ImageService;
    protected  POwningUserNonPkAccessChecker pOwningUserNonPkAccessChecker;

    @Value("${spring.profiles.active}")
    String PROFILE;


    public SProduct(RProduct productRepository, SImageS3 s3ImageService, POwningUserNonPkAccessChecker accessCheckerProxy)
    {
        super(productRepository);
        this.productRepository = productRepository;
        this.s3ImageService = s3ImageService;
        this.pOwningUserNonPkAccessChecker = accessCheckerProxy;
    }

    @Override
    public void post(String authenticatedUser, PostProductDto create_DTO) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, IOException, NotSupportedException, InterruptedException
    {
        Product newProduct = new Product(authenticatedUser, create_DTO);
        newProduct = this.productRepository.saveAndFlush(newProduct);
        List<String> updateImages = Arrays.asList(create_DTO.productImageStrings);
        s3ImageService.put(authenticatedUser,updateImages,newProduct.getSurrogateId(),Product.class);
    }


    @Override
    public ResponseEntity<PutProductDto> get(UUID surrogateId)
    {
        return null;
    }

    @Override
    public void put(String username, PutProductDto update_DTO) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException, IOException, InterruptedException
    {
        UUID surrogateId = update_DTO.surrogateId;
        Product discoveredProduct = this.findBySurrogateId(surrogateId);

        this.pOwningUserNonPkAccessChecker.afterReturningOwningUserNonPrimaryKeyPermissionCheck(username,discoveredProduct);
        discoveredProduct.update(update_DTO);
        productRepository.save(discoveredProduct);
        List<String> imageList = Arrays.asList(update_DTO.productImageStrings);
        this.s3ImageService.put(username,imageList,surrogateId,Product.class);
    }


    @Override
    public void delete(String username,UUID secondary_id) throws Http400Exception, Http401Exception, Http403Exception, UnsupportedOperationException, NotSupportedException
    {
        Product discoveredProduct = this.findBySurrogateId(secondary_id);
        this.pOwningUserNonPkAccessChecker.afterReturningOwningUserNonPrimaryKeyPermissionCheck(username,discoveredProduct);

        if (discoveredProduct ==  null)
            throw new Http403Exception(String.format(CANNOT_PERFORM_OPERATION, username,"DELETE",secondary_id));

        List<Integer> deleteIndexList = this.createZeroIndexedRange(Product.MAXIMUM_IMAGES);
        productRepository.delete(discoveredProduct);
        this.s3ImageService.delete(username,deleteIndexList,secondary_id,Product.class);
    }

    public ResponseEntity<List<PutProductDto>> findAllByUsername(String owning_username) throws NotSupportedException, IOException
    {
        List<Product> productEntityList = productRepository.findAllByOwningUsername(owning_username);
        List<PutProductDto> returnList = new ArrayList<>();

        if(productEntityList.size() > 0) {
            String [] imageIterator = null;
            for (Product product : productEntityList) {
                if(PROFILE.equals("dev")) imageIterator = this.s3ImageService.getTestProductImageArray();
                else imageIterator = this.s3ImageService.getMultipleImages(Product.class,owning_username,Product.MAXIMUM_IMAGES,product.getSurrogateId());
                returnList.add(new PutProductDto(product,imageIterator));
            }
        }


        return  ResponseEntity.ok(returnList);
    }





    protected List<Integer> createZeroIndexedRange(Integer maximum)
    {
        List<Integer> rangeList = new ArrayList<>();

        for(int i  = 0; i < maximum; i++)
            rangeList.add(i);

        //Arrays.asList(new  int [] {1,2});
        //List<Integer> list =  new int[]{1,2,3,4,5};

        return rangeList;
    }
}
