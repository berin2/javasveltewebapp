package com.svelteup.app.backend.utils.services.interfaces;

import com.svelteup.app.backend.utils.events.OwningUserEvent;

/**
 * IUserLifeCycle provides two call back methods for initializing and destorying verified users.
 * @param <PutDtoEventObject> the event type extending owningusernameevent to pass to the init function
 */
public interface IUserLifeCycle<Entity,PutDtoEventObject> {
    Entity initializeUser(PutDtoEventObject initUserData);
    void destroyVerifiedUser(String userToDestroy);
}
