package com.arivanamin.library.backend.patron.domain.command;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeletePatronCommandTest implements BaseUnitTest {
    
    private PatronPersistence persistence;
    private DeletePatronCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = Mockito.mock(PatronPersistence.class);
        command = new DeletePatronCommand(persistence);
    }
    
    @Test
    void shouldDeletePatronWhenIdIsProvided () {
        // Given
        UUID bookId = UUID.randomUUID();
        
        // When
        command.execute(bookId);
        
        // Then
        verify(persistence, times(1)).delete(bookId);
    }
}
