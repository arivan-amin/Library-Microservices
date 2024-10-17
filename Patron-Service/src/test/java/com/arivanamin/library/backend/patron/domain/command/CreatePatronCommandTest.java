package com.arivanamin.library.backend.patron.domain.command;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreatePatronCommandTest implements BaseUnitTest {
    
    private PatronPersistence persistence;
    private CreatePatronCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = mock(PatronPersistence.class);
        command = new CreatePatronCommand(persistence);
    }
    
    @Test
    void shouldReturnIdWhenPatronIsCreated () {
        // Given
        Patron book = RANDOM.nextObject(Patron.class);
        UUID expectedId = UUID.randomUUID();
        when(persistence.create(book)).thenReturn(expectedId);
        
        // When
        UUID actualId = command.execute(book);
        
        // Then
        assertEquals(expectedId, actualId);
        verify(persistence, times(1)).create(book);
    }
}
