package com.arivanamin.library.backend.book.application.endpoints;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateBookRequest {
    
    @JsonIgnore
    ModelMapper mapper = new ModelMapper();
    
    private String isbn;
    private String title;
    private String author;
    private String publicationYear;
    
    public Book toDomain () {
        return mapper.map(this, Book.class);
    }
}
