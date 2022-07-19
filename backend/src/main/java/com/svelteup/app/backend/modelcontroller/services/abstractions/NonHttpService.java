package com.svelteup.app.backend.modelcontroller.services.abstractions;

import com.svelteup.app.backend.productorder.models.ProductReviewScoreCard;
import com.svelteup.app.backend.security.models.SvelteUpUser;

import javax.transaction.NotSupportedException;
import java.util.UUID;

public interface NonHttpService<Entity>{
    Entity get(String authenticatedUser,UUID surrogateid) throws NotSupportedException;
    Entity post(SvelteUpUser user) throws NotSupportedException;
    void  delete(String authenticatedUser,UUID surrogateId) throws NotSupportedException;
}
