package com.arivanamin.library.backend.patron.domain.query;

import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadPatronsQuery {
    
    private final PatronPersistence persistence;
    
    @LogExecutionTime
    public List<Patron> execute () {
        return persistence.findAll();
    }
}
