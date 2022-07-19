package com.svelteup.app.backend.testing.services;

import com.svelteup.app.backend.testing.services.owninguserdatasetup.AOwningUserDataSetup;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import com.svelteup.app.backend.testing.services.owninguserdatasetup.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SOwningUserSetup {

    @Value("${datasetup.minimum_index}") Integer minimum;
    @Value("${datasetup.maximum_index}") Integer maximum;
    @Value("${datasetup.user_count}") Integer userCount;

    List<AOwningUserDataSetup> testDataInitializerList;
    List<OwningUserDataSetupDto> owningUserDataSetupDtoList;

    @Autowired SSystemEntitySetupOwningUser sSystemEntitySetup;
    @Autowired SSvelteUpUserSetupOwningUser sSvelteUpUserSetupOwningUser;
    @Autowired SSvelteUpUserProfileSetupOwningUser sSvelteUpUserProfileSetupOwningUser;
    @Autowired SProductSetupOwningUser sProductSetupOwningUser;
    @Autowired SProductReviewCard sProductReviewCard;
    @Autowired SCustomerPaymentSetupOwningUser sCustomerPaymentSetupOwningUser;

    public SOwningUserSetup()
    {
        testDataInitializerList  = new ArrayList<>();
        owningUserDataSetupDtoList = new ArrayList<>();
    }


    @PostConstruct
    public void initServiceList()
    {

        testDataInitializerList.add(sSystemEntitySetup);
        testDataInitializerList.add(sSvelteUpUserSetupOwningUser);
        testDataInitializerList.add(sProductReviewCard);
        testDataInitializerList.add(sSvelteUpUserProfileSetupOwningUser);
        testDataInitializerList.add(sProductSetupOwningUser);
        testDataInitializerList.add(sCustomerPaymentSetupOwningUser);
  }

  public List<OwningUserDataSetupDto> setupData() throws IOException, NotSupportedException, InterruptedException {
      for(int i = 0; i < userCount; i++)
      {
          OwningUserDataSetupDto dataSetupDto = new OwningUserDataSetupDto(minimum,maximum,i);
          for(AOwningUserDataSetup setupClass  : this.testDataInitializerList)
              setupClass.setupEntity(dataSetupDto);

          this.owningUserDataSetupDtoList.add(dataSetupDto);
      }

      return this.owningUserDataSetupDtoList;
  }


}
