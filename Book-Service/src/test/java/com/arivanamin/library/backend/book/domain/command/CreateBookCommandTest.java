package com.arivanamin.library.backend.book.domain.command;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateBookCommandTest implements BaseUnitTest {
    
    private BookPersistence persistence;
    private CreateBookCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = mock(BookPersistence.class);
        command = new CreateBookCommand(persistence);
    }
    
    @Test
    void shouldReturnIdWhenBookIsCreated () {
        // Given
        Book book = RANDOM.nextObject(Book.class);
        UUID expectedId = UUID.randomUUID();
        when(persistence.create(book)).thenReturn(expectedId);
        
        // When
        UUID actualId = command.execute(book);
        
        // Then
        assertEquals(expectedId, actualId);
        verify(persistence, times(1)).create(book);
    }
}
