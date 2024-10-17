package com.arivanamin.library.backend.patron.domain.command;

import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletePatronCommand {
    
    private final PatronPersistence persistence;
    
    @LogExecutionTime
    public void execute (UUID id) {
        persistence.delete(id);
    }
}
