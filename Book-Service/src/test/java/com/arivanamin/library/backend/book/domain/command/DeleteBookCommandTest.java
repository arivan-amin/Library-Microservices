package com.arivanamin.library.backend.book.domain.command;

import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteBookCommandTest implements BaseUnitTest {
    
    private BookPersistence persistence;
    private DeleteBookCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = Mockito.mock(BookPersistence.class);
        command = new DeleteBookCommand(persistence);
    }
    
    @Test
    void shouldDeleteBookWhenIdIsProvided () {
        // Given
        UUID bookId = UUID.randomUUID();
        
        // When
        command.execute(bookId);
        
        // Then
        verify(persistence, times(1)).delete(bookId);
    }
}
