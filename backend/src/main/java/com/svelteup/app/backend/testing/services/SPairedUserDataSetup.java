package com.svelteup.app.backend.testing.services;

import com.svelteup.app.backend.security.models.SvelteUpUser;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import com.svelteup.app.backend.testing.dto.PairedOwningUserDataSetupDto;
import com.svelteup.app.backend.testing.services.paireduserdatasetup.*;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@ApiModel("Represents service class used to setup paired user entites such as notifications and orders.")
public class SPairedUserDataSetup {

    @Value("${datasetup.paired_minimum_index}")
    Integer minimum;
    @Value("${datasetup.paired_maximum_index}")
    Integer maximum;
    @Value("${datasetup.user_pass_count}")
    Integer userSetPassCount;
    List<APairedOwningUserDataSetup> owningUserDataSetupDtoList;
    List<OwningUserDataSetupDto> setupUserList;

    @Autowired SMessageChatSetupOwningUser sMessageChatSetupOwningUser;
    @Autowired SProductOrderSetupOwningUser sProductOrderSetupOwningUser;
    @Autowired SProductQuestionSetupOwningUser sProductQuestionSetupOwningUser;
    @Autowired SProductReviewSetupOwningUser sProductReviewSetupOwningUser;

    public SPairedUserDataSetup()
    {
        owningUserDataSetupDtoList = new ArrayList<>();
    }

    @PostConstruct
    public void initServiceList()
    {
        owningUserDataSetupDtoList.add(sMessageChatSetupOwningUser);
        owningUserDataSetupDtoList.add(sProductOrderSetupOwningUser);
        owningUserDataSetupDtoList.add(sProductQuestionSetupOwningUser);
        owningUserDataSetupDtoList.add(sProductReviewSetupOwningUser);
    }

    public Integer generateRandomInteger(Integer listSize, Integer forbiddenInteger)
    {
        Integer returnInteger = 0;
        Integer buffer = 0;
        boolean integerGenerated = listSize <= 1 ? true : false;

        while(!integerGenerated)
        {
            buffer = ThreadLocalRandom.current().nextInt(0,listSize);
            integerGenerated = !buffer.equals(forbiddenInteger);
            returnInteger = integerGenerated ? buffer : returnInteger;
        }
        return returnInteger;
    }
    public  void setupData(List<OwningUserDataSetupDto> owningUserDataSetupDtoList) throws IOException, NotSupportedException, InterruptedException
    {
        this.setupUserList  = owningUserDataSetupDtoList;

        for(int i = 0; i < userSetPassCount; i++)
        {
            Integer owningUserIndex = generateRandomInteger(owningUserDataSetupDtoList.size(), null);
            Integer secondaryOwningUserIndex = generateRandomInteger(owningUserDataSetupDtoList.size(),owningUserIndex);
            PairedOwningUserDataSetupDto pairedOwningUserDataSetupDto = new PairedOwningUserDataSetupDto(minimum,maximum);
            pairedOwningUserDataSetupDto.chosenOwningUser = owningUserDataSetupDtoList.get(owningUserIndex);
            pairedOwningUserDataSetupDto.chosenSecondaryOwningUser = owningUserDataSetupDtoList.get(secondaryOwningUserIndex);

            for (APairedOwningUserDataSetup pairedOwningUserDataSetup : this.owningUserDataSetupDtoList)
                pairedOwningUserDataSetup.setupEntity(pairedOwningUserDataSetupDto);
        }
    }

}
