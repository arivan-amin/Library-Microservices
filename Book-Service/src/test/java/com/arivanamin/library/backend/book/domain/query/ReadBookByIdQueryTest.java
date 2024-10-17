package com.arivanamin.library.backend.book.domain.query;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.exception.BookNotFoundException;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReadBookByIdQueryTest implements BaseUnitTest {
    
    private BookPersistence persistence;
    private ReadBookByIdQuery query;
    
    @BeforeEach
    void setUp () {
        persistence = mock(BookPersistence.class);
        query = new ReadBookByIdQuery(persistence);
    }
    
    @Test
    void shouldReturnBookWhenFoundById () {
        // Given
        UUID bookId = UUID.randomUUID();
        Book expectedBook = RANDOM.nextObject(Book.class);
        when(persistence.findById(bookId)).thenReturn(Optional.of(expectedBook));
        
        // When
        Book actualBook = query.execute(bookId);
        
        // Then
        assertEquals(expectedBook, actualBook);
        verify(persistence, times(1)).findById(bookId);
    }
    
    @Test
    void shouldThrowExceptionWhenBookIsNotFound () {
        // Given
        UUID bookId = UUID.randomUUID();
        when(persistence.findById(bookId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BookNotFoundException.class, () -> query.execute(bookId));
        verify(persistence, times(1)).findById(bookId);
    }
}
