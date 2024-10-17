package com.arivanamin.library.backend.book.domain.command;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateBookCommand {
    
    private final BookPersistence persistence;
    
    @LogExecutionTime
    public void execute (UUID id, Book book) {
        book.setId(id);
        persistence.update(id, book);
    }
}
