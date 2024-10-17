package com.arivanamin.library.backend.patron.application.endpoints;

import com.arivanamin.library.backend.patron.domain.entity.Patron;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ReadPatronsResponse {
    
    List<PatronResponse> responses;
    
    public static ReadPatronsResponse of (List<Patron> books) {
        return new ReadPatronsResponse(books.stream().map(PatronResponse::of).toList());
    }
}
