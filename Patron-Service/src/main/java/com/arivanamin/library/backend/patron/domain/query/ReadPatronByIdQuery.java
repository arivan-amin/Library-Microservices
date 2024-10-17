package com.arivanamin.library.backend.patron.domain.query;

import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.exception.PatronNotFoundException;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadPatronByIdQuery {
    
    private final PatronPersistence persistence;
    
    @LogExecutionTime
    public Patron execute (UUID id) {
        return persistence.findById(id).orElseThrow(PatronNotFoundException::new);
    }
}
