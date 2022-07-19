package com.svelteup.app.backend.testing.services.paireduserdatasetup;

import com.svelteup.app.backend.modelcontroller.models.Product;
import com.svelteup.app.backend.modelcontroller.models.ProductQuestion;
import com.svelteup.app.backend.modelcontroller.repositories.RProductQuestion;
import com.svelteup.app.backend.testing.dto.PairedOwningUserDataSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.IOException;

@Service
public class SProductQuestionSetupOwningUser extends APairedOwningUserDataSetup {
    @Autowired RProductQuestion rProductQuestion;
    @Override
    public void setupEntity(PairedOwningUserDataSetupDto dto) throws NotSupportedException, IOException, InterruptedException {

        for(Product productToQuestion: dto.chosenSecondaryOwningUser.productList)
            for(int i =  0; i < dto.getRandomIntegerWithinRange();i++)
            {
                ProductQuestion question = new ProductQuestion(dto.chosenOwningUser.owningUser.getUsername(), productToQuestion,"This is system test question? Do you know about..." + this.fakeItTillYouMakeIt.cat().name() + "??");
                question = rProductQuestion.save(question);
            }
    }
}
