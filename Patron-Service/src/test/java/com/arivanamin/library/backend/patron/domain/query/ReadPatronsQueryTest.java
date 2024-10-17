package com.arivanamin.library.backend.patron.domain.query;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ReadPatronsQueryTest implements BaseUnitTest {
    
    private PatronPersistence persistence;
    private ReadPatronsQuery readPatronsQuery;
    
    @BeforeEach
    void setUp () {
        persistence = mock(PatronPersistence.class);
        readPatronsQuery = new ReadPatronsQuery(persistence);
    }
    
    @Test
    void shouldReturnAllPatrons () {
        // Given
        Patron patron1 = RANDOM.nextObject(Patron.class);
        Patron patron2 = RANDOM.nextObject(Patron.class);
        List<Patron> expectedPatrons = Arrays.asList(patron1, patron2);
        when(persistence.findAll()).thenReturn(expectedPatrons);
        
        // When
        List<Patron> actualPatrons = readPatronsQuery.execute();
        
        // Then
        assertEquals(expectedPatrons, actualPatrons);
        verify(persistence, times(1)).findAll();
    }
    
    @Test
    void shouldReturnEmptyListWhenNoPatronsAreFound () {
        // Given
        when(persistence.findAll()).thenReturn(List.of());
        
        // When
        List<Patron> actualPatrons = readPatronsQuery.execute();
        
        // Then
        assertTrue(actualPatrons.isEmpty());
        verify(persistence, times(1)).findAll();
    }
}
