package com.arivanamin.library.backend.book.domain.query;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.exception.BookNotFoundException;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadBookByIdQuery {
    
    private final BookPersistence persistence;
    
    @LogExecutionTime
    public Book execute (UUID id) {
        return persistence.findById(id).orElseThrow(BookNotFoundException::new);
    }
}
