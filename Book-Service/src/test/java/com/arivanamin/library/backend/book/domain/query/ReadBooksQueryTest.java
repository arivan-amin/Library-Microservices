package com.arivanamin.library.backend.book.domain.query;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ReadBooksQueryTest implements BaseUnitTest {
    
    private BookPersistence persistence;
    private ReadBooksQuery readBooksQuery;
    
    @BeforeEach
    void setUp () {
        persistence = mock(BookPersistence.class);
        readBooksQuery = new ReadBooksQuery(persistence);
    }
    
    @Test
    void shouldReturnAllBooks () {
        // Given
        Book book1 = RANDOM.nextObject(Book.class);
        Book book2 = RANDOM.nextObject(Book.class);
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(persistence.findAll()).thenReturn(expectedBooks);
        
        // When
        List<Book> actualBooks = readBooksQuery.execute();
        
        // Then
        assertEquals(expectedBooks, actualBooks);
        verify(persistence, times(1)).findAll();
    }
    
    @Test
    void shouldReturnEmptyListWhenNoBooksAreFound () {
        // Given
        when(persistence.findAll()).thenReturn(List.of());
        
        // When
        List<Book> actualBooks = readBooksQuery.execute();
        
        // Then
        assertTrue(actualBooks.isEmpty());
        verify(persistence, times(1)).findAll();
    }
}
