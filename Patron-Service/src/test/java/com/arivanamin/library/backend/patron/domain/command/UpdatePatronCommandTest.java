package com.arivanamin.library.backend.patron.domain.command;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UpdatePatronCommandTest implements BaseUnitTest {
    
    private PatronPersistence persistence;
    private UpdatePatronCommand command;
    
    @BeforeEach
    void setUp () {
        persistence = mock(PatronPersistence.class);
        command = new UpdatePatronCommand(persistence);
    }
    
    @Test
    void shouldUpdatePatronWhenIdAndPatronAreProvided () {
        // Given
        UUID bookId = UUID.randomUUID();
        Patron book = RANDOM.nextObject(Patron.class);
        
        // When
        command.execute(bookId, book);
        
        // Then
        assertEquals(bookId, book.getId());
        verify(persistence, times(1)).update(bookId, book);
    }
}
