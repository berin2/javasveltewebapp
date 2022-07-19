package com.svelteup.app.backend.productorder.services;

import com.svelteup.app.backend.aop.aspects.owninguserpk.OwningUserPrimaryKeyPermissionAOPTarget;
import com.svelteup.app.backend.modelcontroller.controllers.controllerexceptions.Http403Exception;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntity;
import com.svelteup.app.backend.modelcontroller.services.abstractions.NonHttpService;
import com.svelteup.app.backend.modelcontroller.services.abstractions.SSurrogateEntityOwningUserPk;
import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.productorder.repositories.RProductReviewScoreCard;
import com.svelteup.app.backend.security.models.SvelteUpUser;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.UUID;

@Service
public class SProductReviewScoreCard extends SSurrogateEntityOwningUserPk<ProductReviewScoreCard> implements NonHttpService<ProductReviewScoreCard>
{
    protected RProductReviewScoreCard productReviewScoreCardRepository;

    public SProductReviewScoreCard(RProductReviewScoreCard productReviewScoreCardRepository)
    {
        super(productReviewScoreCardRepository);
    }
    @Override
    public ProductReviewScoreCard get(String authenticatedUser, UUID surrogateid) throws NotSupportedException {
        ProductReviewScoreCard discoveredCard = this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);

        return discoveredCard;
    }

    @Override
    public ProductReviewScoreCard post(SvelteUpUser user) {
        ProductReviewScoreCard newScoreCard = new ProductReviewScoreCard(user);
        this.repository.saveAndFlush(newScoreCard);
        return newScoreCard;
    }

    public void put(String authenticatedUser, Integer oldReviewScore, Integer newReviewScore) throws NotSupportedException {
        //If the review scores are different then fetch and update score card. Otherwise, no need to update score card.
        if(!oldReviewScore.equals(newReviewScore)) {
            ProductReviewScoreCard scoreCard = this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);

            switch (oldReviewScore) {
                case 5: scoreCard.setFiveStarReviews(scoreCard.getFiveStarReviews() - 1);break;
                case 4: scoreCard.setFourStarReviews(scoreCard.getFourStarReviews() - 1);break;
                case 3: scoreCard.setThreeStarReviews(scoreCard.getThreeStarReviews() - 1);break;
                case 2: scoreCard.setTwoStarReviews(scoreCard.getTwoStarReviews() - 1);break;
                case 1: scoreCard.setOneStarReviews(scoreCard.getOneStarReviews() - 1);break;
            }

            switch (newReviewScore) {
                case 5: scoreCard.setFiveStarReviews(scoreCard.getFiveStarReviews() + 1);break;
                case 4: scoreCard.setFourStarReviews(scoreCard.getFourStarReviews() + 1);break;
                case 3: scoreCard.setThreeStarReviews(scoreCard.getThreeStarReviews() + 1);break;
                case 2: scoreCard.setTwoStarReviews(scoreCard.getTwoStarReviews() + 1);break;
                case 1: scoreCard.setOneStarReviews(scoreCard.getOneStarReviews() + 1);break;
            }

            this.productReviewScoreCardRepository.save(scoreCard);
        }

    }

    @Override
    public void delete(String authenticatedUser, UUID surrogateId) throws NotSupportedException {
        ProductReviewScoreCard discoveredCard = this.findBySurrogateIdWithCheck(authenticatedUser,authenticatedUser);
        this.productReviewScoreCardRepository.delete(discoveredCard);
    }

}
