package com.arivanamin.library.backend.patron.application.endpoints;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreatePatronResponse {
    
    UUID id;
    
    public static CreatePatronResponse of (UUID id) {
        return new CreatePatronResponse(id);
    }
}
