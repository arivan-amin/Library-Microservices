package com.arivanamin.library.backend.borrow.domain.command;

import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;
import com.arivanamin.library.backend.borrow.domain.exception.BookAlreadyReturnedException;
import com.arivanamin.library.backend.borrow.domain.persistence.BorrowPersistence;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReturnBookCommandTest implements BaseUnitTest {
    
    private BorrowPersistence persistence;
    private ReturnBookCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = mock(BorrowPersistence.class);
        command = new ReturnBookCommand(persistence);
    }
    
    @Test
    void shouldUpdateRecordToReturnedWhenBookIsBorrowed () {
        // Given
        BorrowBookRequest request = RANDOM.nextObject(BorrowBookRequest.class);
        BorrowRecord borrowedRecord = new BorrowRecord();
        when(persistence.findBorrowedBookRecord(request)).thenReturn(Optional.of(borrowedRecord));
        
        // When
        command.execute(request);
        
        // Then
        verify(persistence, times(1)).updateRecordToReturned(borrowedRecord);
        verify(persistence, times(1)).findBorrowedBookRecord(request);
    }
    
    @Test
    void shouldThrowExceptionWhenBookIsAlreadyReturned () {
        // Given
        BorrowBookRequest request = RANDOM.nextObject(BorrowBookRequest.class);
        when(persistence.findBorrowedBookRecord(request)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BookAlreadyReturnedException.class, () -> command.execute(request));
        verify(persistence, times(1)).findBorrowedBookRecord(request);
        verify(persistence, never()).updateRecordToReturned(any(BorrowRecord.class));
    }
}
