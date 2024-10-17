package com.arivanamin.library.backend.patron.application.endpoints;

import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatronRequest {
    
    @JsonIgnore
    ModelMapper mapper = new ModelMapper();
    
    private String name;
    private String contactInformation;
    
    public Patron toDomain () {
        return mapper.map(this, Patron.class);
    }
}
