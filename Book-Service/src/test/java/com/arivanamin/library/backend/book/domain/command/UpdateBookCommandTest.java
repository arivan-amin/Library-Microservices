package com.arivanamin.library.backend.book.domain.command;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UpdateBookCommandTest implements BaseUnitTest {
    
    private BookPersistence persistence;
    private UpdateBookCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = mock(BookPersistence.class);
        command = new UpdateBookCommand(persistence);
    }
    
    @Test
    void shouldUpdateBookWhenIdAndBookAreProvided () {
        // Given
        UUID bookId = UUID.randomUUID();
        Book book = RANDOM.nextObject(Book.class);
        
        // When
        command.execute(bookId, book);
        
        // Then
        assertEquals(bookId, book.getId());
        verify(persistence, times(1)).update(bookId, book);
    }
}
