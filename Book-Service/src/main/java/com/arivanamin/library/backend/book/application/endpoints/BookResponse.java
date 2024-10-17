package com.arivanamin.library.backend.book.application.endpoints;

import com.arivanamin.library.backend.book.domain.entity.Book;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
class BookResponse {
    
    private UUID id;
    private String isbn;
    private String title;
    private String author;
    private String publicationYear;
    
    public static BookResponse of (Book book) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(book, BookResponse.class);
    }
}
