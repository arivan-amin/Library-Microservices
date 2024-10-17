package com.arivanamin.library.backend.patron.domain.query;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.exception.PatronNotFoundException;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReadPatronByIdQueryTest implements BaseUnitTest {
    
    private PatronPersistence persistence;
    private ReadPatronByIdQuery query;
    
    @BeforeEach
    void setUp () {
        persistence = mock(PatronPersistence.class);
        query = new ReadPatronByIdQuery(persistence);
    }
    
    @Test
    void shouldReturnPatronWhenFoundById () {
        // Given
        UUID patronId = UUID.randomUUID();
        Patron expectedPatron = RANDOM.nextObject(Patron.class);
        when(persistence.findById(patronId)).thenReturn(Optional.of(expectedPatron));
        
        // When
        Patron actualPatron = query.execute(patronId);
        
        // Then
        assertEquals(expectedPatron, actualPatron);
        verify(persistence, times(1)).findById(patronId);
    }
    
    @Test
    void shouldThrowExceptionWhenPatronIsNotFound () {
        // Given
        UUID patronId = UUID.randomUUID();
        when(persistence.findById(patronId)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(PatronNotFoundException.class, () -> query.execute(patronId));
        verify(persistence, times(1)).findById(patronId);
    }
}
