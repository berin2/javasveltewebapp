package com.svelteup.app.backend.testing.services.paireduserdatasetup;

import com.github.javafaker.Faker;
import com.svelteup.app.backend.testing.dto.OwningUserDataSetupDto;
import com.svelteup.app.backend.testing.dto.PairedOwningUserDataSetupDto;

import javax.transaction.NotSupportedException;
import java.io.IOException;

public abstract class APairedOwningUserDataSetup {
    public Faker fakeItTillYouMakeIt  = new Faker();
    public abstract void setupEntity(PairedOwningUserDataSetupDto pairedOwningUserDataSetupDto) throws IOException, NotSupportedException, InterruptedException;


}
