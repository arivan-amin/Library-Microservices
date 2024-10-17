package com.arivanamin.library.backend.patron.application.endpoints;

import com.arivanamin.library.backend.patron.domain.entity.Patron;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
class PatronResponse {
    
    private UUID id;
    private String name;
    private String contactInformation;
    
    public static PatronResponse of (Patron book) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(book, PatronResponse.class);
    }
}
