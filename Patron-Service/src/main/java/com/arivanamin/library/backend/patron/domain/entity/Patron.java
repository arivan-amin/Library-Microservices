package com.arivanamin.library.backend.patron.domain.entity;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patron {
    
    private UUID id;
    private String name;
    private String contactInformation;
}
