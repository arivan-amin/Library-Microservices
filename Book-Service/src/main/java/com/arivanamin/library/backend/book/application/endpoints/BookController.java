package com.arivanamin.library.backend.book.application.endpoints;

import com.arivanamin.library.backend.book.domain.command.*;
import com.arivanamin.library.backend.book.domain.query.ReadBookByIdQuery;
import com.arivanamin.library.backend.book.domain.query.ReadBooksQuery;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping ("/api/books")
@RequiredArgsConstructor
@Cacheable
@Slf4j
public class BookController {
    
    private final ReadBooksQuery readQuery;
    private final ReadBookByIdQuery readByIdQuery;
    private final CreateBookCommand createCommand;
    private final UpdateBookCommand updateCommand;
    private final DeleteBookCommand deleteCommand;
    
    @GetMapping ("")
    @Operation (summary = "Get a list of all books in catalog")
    @ResponseStatus (HttpStatus.OK)
    public ReadBooksResponse getAllBooks () {
        return ReadBooksResponse.of(readQuery.execute());
    }
    
    @GetMapping ("/{id}")
    @Operation (summary = "Get a single book by id")
    @ResponseStatus (HttpStatus.OK)
    public BookResponse getBookById (@PathVariable UUID id) {
        return BookResponse.of(readByIdQuery.execute(id));
    }
    
    @PostMapping ("")
    @Operation (summary = "Creates a book in catalog")
    @ResponseStatus (HttpStatus.CREATED)
    public CreateBookResponse createBook (@RequestBody CreateBookRequest request) {
        UUID createdBookId = createCommand.execute(request.toDomain());
        return CreateBookResponse.of(createdBookId);
    }
    
    @PutMapping ("/{id}")
    @Operation (summary = "Updates a book in catalog")
    @ResponseStatus (HttpStatus.OK)
    public void updateBook (@PathVariable UUID id, @RequestBody CreateBookRequest request) {
        updateCommand.execute(id, request.toDomain());
    }
    
    @DeleteMapping ("/{id}")
    @Operation (summary = "Deletes a book in catalog")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deleteBook (@PathVariable UUID id) {
        deleteCommand.execute(id);
    }
}
