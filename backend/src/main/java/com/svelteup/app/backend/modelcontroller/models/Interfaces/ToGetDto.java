package com.svelteup.app.backend.modelcontroller.models.Interfaces;

/**
 * ToGetDto represents an interface which entites can implement to convert themselves into a GetDto Representation.
 * @param <EntityDto> The GetDto to return.
 */
public interface ToGetDto <EntityDto>{
    EntityDto toGetDto();
}
