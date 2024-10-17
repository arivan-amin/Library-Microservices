package com.arivanamin.library.backend.book.domain.query;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadBooksQuery {
    
    private final BookPersistence persistence;
    
    @LogExecutionTime
    public List<Book> execute () {
        return persistence.findAll();
    }
}
