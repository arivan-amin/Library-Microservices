package com.arivanamin.library.backend.book.domain.entity;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    private UUID id;
    private String isbn;
    private String title;
    private String author;
    private String publicationYear;
}
