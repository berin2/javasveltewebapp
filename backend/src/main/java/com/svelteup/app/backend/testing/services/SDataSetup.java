package com.svelteup.app.backend.testing.services;

import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.List;

@Service
public class SDataSetup {
    @Autowired SOwningUserSetup sOwningUserSetup;
    @Autowired SPairedUserDataSetup sPairedUserDataSetup;

    @PostConstruct
    public void setupData() throws IOException, NotSupportedException, InterruptedException {
        List<OwningUserDataSetupDto> setupDtoList = sOwningUserSetup.setupData();
        sPairedUserDataSetup.setupData(setupDtoList);
    }
}
