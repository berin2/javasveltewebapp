package com.svelteup.app.backend.testing.dto;

import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.payment.models.CustomerPaymentInfo;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.security.models.Authority;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ApiModel("Represents class used to setup a single user and the set of associated entities for that user.")
public class OwningUserDataSetupDto extends BaseDataSetupDto {
    public Integer userIndex;

    public OwningUserDataSetupDto(Integer minimum, Integer maximum, Integer userIndex)
    {
       super(minimum, maximum);
       this.userIndex = userIndex;
       this.productList = new ArrayList<>();
       this.productQuestion = new ArrayList<>();
    }

    public Integer getRandomIntegerWithinRange()
    {
        return ThreadLocalRandom.current().nextInt(minimum,maximum);
    }

    public SvelteUpUser owningUser;
    public Authority fullySetupAuthority;
    public Authority notFullySetupAuthority;

    public SvelteUpUserProfile owningUserProfile;
    public SvelteUpUserProfile secondaryOwningUserProfile;

    public ProductReviewScoreCard owningUserProductReviewScoreCard;

    public CustomerPaymentInfo owningUserCustomerPayment;

    public List<ProductQuestion> productQuestion;
    public List<Product> productList;




}
