package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.braintreegateway.Customer;
import com.svelteup.app.backend.payment.services.SCustomer;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.productorder.repositories.RProductReviewScoreCard;
import com.svelteup.app.backend.profile.models.SvelteUpUserProfile;
import com.svelteup.app.backend.profile.repositories.RSvelteUpUserProfile;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.security.repositories.AuthorityRepository;
import com.svelteup.app.backend.security.repositories.RSvelteUpUser;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Qualifier("SSystemEntitySetupOwningUser")
@NoArgsConstructor
public class SSystemEntitySetupOwningUser extends AOwningUserDataSetup {
    @Autowired RSvelteUpUserProfile rSvelteUpUserProfile;
    @Autowired RProductReviewScoreCard rProductReviewScoreCard;
    @Autowired RSvelteUpUser userRepository;
    @Autowired SCustomer customerService;
    @Autowired AuthorityRepository authorityRepository;

    public static Customer TEST_SYSTEM_CUSTOMER;
    public static SvelteUpUser TEST_SYSTEM_USER;
    public static SvelteUpUserProfile  TEST_SYSTEM_PROFILE;





    @Override
    public void setupEntity(OwningUserDataSetupDto owningUserDataSetupDto) {

    }

    @PostConstruct
    public void initTestSystemGlobalEntities()
    {
        initTestSystemEntites();
    }


    protected void initTestSystemEntites()
    {
        TEST_SYSTEM_USER = new SvelteUpUser("TEST_SYSTEM","TEST_SYSTEM");
        TEST_SYSTEM_USER = this.userRepository.save(TEST_SYSTEM_USER);


        TEST_SYSTEM_PROFILE =  new SvelteUpUserProfile(TEST_SYSTEM_USER);
        TEST_SYSTEM_PROFILE.setFirstName("TEST");
        TEST_SYSTEM_PROFILE.setLastName("SYSTEM");
        TEST_SYSTEM_PROFILE.setAddressLineOne("0.0.0.0");
        TEST_SYSTEM_PROFILE.setUserProfileCity("Mboiok");
        TEST_SYSTEM_PROFILE.setUserProfileState("Lx");
        TEST_SYSTEM_PROFILE.setEmail("TEST_SYSTEM@testsystem.testsytem");
        TEST_SYSTEM_PROFILE.setUserProfileZipCode(7777777);
        TEST_SYSTEM_PROFILE.setAboutMe("TEST SYSTEM");

        ProductReviewScoreCard reviewScoreCard = new ProductReviewScoreCard(TEST_SYSTEM_USER);
        rProductReviewScoreCard.save(reviewScoreCard);

        TEST_SYSTEM_PROFILE.setProductReviewScoreCard(reviewScoreCard);
        TEST_SYSTEM_PROFILE = rSvelteUpUserProfile.save(TEST_SYSTEM_PROFILE);
        TEST_SYSTEM_CUSTOMER = customerService.intializeEntity(TEST_SYSTEM_USER.getUsername(),TEST_SYSTEM_PROFILE);;
    }


}
