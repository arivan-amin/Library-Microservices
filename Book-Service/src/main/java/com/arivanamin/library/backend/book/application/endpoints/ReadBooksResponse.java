package com.arivanamin.library.backend.book.application.endpoints;

import com.arivanamin.library.backend.book.domain.entity.Book;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ReadBooksResponse {
    
    List<BookResponse> responses;
    
    public static ReadBooksResponse of (List<Book> books) {
        return new ReadBooksResponse(books.stream().map(BookResponse::of).toList());
    }
}
