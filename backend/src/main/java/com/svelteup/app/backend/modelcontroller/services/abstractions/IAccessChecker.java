package com.svelteup.app.backend.modelcontroller.services.abstractions;

import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;

import javax.swing.text.html.parser.Entity;
import javax.transaction.NotSupportedException;
import java.util.UUID;

/**
 * IAccessChecker is used in conjuction with Repository classes and AOP to define a data fetching and access checking
 * method findBySurrogateIdWithCheck.
 * @param <EntityId> The id type to query the database, usually the surrogate UUID but can be the username as well.
 * @param <ReturningEntityType> The returning type of the entity.
 */
public interface IAccessChecker<EntityId,ReturningEntityType> {
     ReturningEntityType findBySurrogateIdWithCheck(String authenticatedUser, EntityId lookUpId) throws NotSupportedException;
}
