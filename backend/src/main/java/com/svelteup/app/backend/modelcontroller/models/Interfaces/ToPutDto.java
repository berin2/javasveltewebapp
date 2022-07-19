package com.svelteup.app.backend.modelcontroller.models.Interfaces;

public interface ToPutDto<PostDto> {
    public void update(PostDto updateDto);
    public PostDto toExistingDto();
}
