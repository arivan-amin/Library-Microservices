package com.arivanamin.library.backend.book.domain.command;

import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBookCommand {
    
    private final BookPersistence persistence;
    
    @LogExecutionTime
    public void execute (UUID id) {
        persistence.delete(id);
    }
}
