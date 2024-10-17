package com.arivanamin.library.backend.borrow.domain.command;

import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;
import com.arivanamin.library.backend.borrow.domain.exception.BookAlreadyBorrowedException;
import com.arivanamin.library.backend.borrow.domain.persistence.BorrowPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowBookCommandTest implements BaseUnitTest {
    
    private BorrowPersistence persistence;
    private BorrowBookCommand borrowBookCommand;
    
    @BeforeEach
    void setUp () {
        persistence = mock(BorrowPersistence.class);
        borrowBookCommand = new BorrowBookCommand(persistence);
    }
    
    @Test
    void shouldThrowExceptionWhenBookIsAlreadyBorrowed () {
        // Given
        BorrowBookRequest request = RANDOM.nextObject(BorrowBookRequest.class);
        when(persistence.findBorrowedBookRecord(request)).thenReturn(
            Optional.of(RANDOM.nextObject(BorrowRecord.class)));
        
        // When & Then
        assertThrows(BookAlreadyBorrowedException.class, () -> borrowBookCommand.execute(request));
        verify(persistence, times(1)).findBorrowedBookRecord(request);
        verify(persistence, never()).createBorrowRecord(any(BorrowRecord.class));
    }
    
    @Test
    void shouldCreateBorrowRecordWhenBookIsNotAlreadyBorrowed () {
        // Given
        ArgumentCaptor<BorrowRecord> captor = ArgumentCaptor.forClass(BorrowRecord.class);
        UUID bookId = UUID.randomUUID();
        UUID patronId = UUID.randomUUID();
        BorrowBookRequest request = new BorrowBookRequest(bookId, patronId);
        when(persistence.findBorrowedBookRecord(request)).thenReturn(Optional.empty());
        
        // When
        borrowBookCommand.execute(request);
        
        // Then
        verify(persistence, times(1)).createBorrowRecord(captor.capture());
        BorrowRecord createdRecord = captor.getValue();
        assertNotNull(createdRecord.getBorrowDateTime());
        assertEquals(bookId, createdRecord.getBookId());
        assertEquals(patronId, createdRecord.getPatronId());
        assertNull(createdRecord.getReturnDateTime());
    }
}
