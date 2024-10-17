package com.arivanamin.library.backend.book.application.endpoints;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreateBookResponse {
    
    UUID id;
    
    public static CreateBookResponse of (UUID id) {
        return new CreateBookResponse(id);
    }
}
