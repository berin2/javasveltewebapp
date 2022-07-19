package com.svelteup.app.backend.testing.services.owninguserdatasetup;

import com.github.javafaker.Faker;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;

import javax.transaction.NotSupportedException;
import java.io.IOException;

public abstract class AOwningUserDataSetup {

    public Faker fakeItTillYouMakeIt  = new Faker();
    public abstract void setupEntity(OwningUserDataSetupDto owningUserDataSetupDto) throws IOException, NotSupportedException, InterruptedException;

}
