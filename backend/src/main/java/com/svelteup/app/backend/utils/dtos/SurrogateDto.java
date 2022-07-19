package com.svelteup.app.backend.utils.dtos;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class SurrogateDto {
    public UUID surrogateId;

    public SurrogateDto(UUID surrogateId)
    {
        this.surrogateId = surrogateId;
    }
}
