package com.arivanamin.library.backend.patron.infrastructure;

import com.arivanamin.library.backend.patron.domain.entity.Patron;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Entity
@Table (name = "Patrons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
class JpaPatron {
    
    private static ModelMapper mapper = new ModelMapper();
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    UUID id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String contactInformation;
    
    public static JpaPatron fromDomain (Patron Patron) {
        return mapper.map(Patron, JpaPatron.class);
    }
    
    public Patron toDomain () {
        return mapper.map(this, Patron.class);
    }
}
