package com.arivanamin.library.backend.patron.infrastructure;

import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Transactional
public class JpaPatronPersistence implements PatronPersistence {
    
    private final PatronRepository repository;
    
    ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public List<Patron> findAll () {
        return repository.findAll().stream().map(JpaPatron::toDomain).toList();
    }
    
    @Override
    public Optional<Patron> findById (UUID id) {
        return repository.findById(id).map(JpaPatron::toDomain);
    }
    
    @Override
    public UUID create (Patron book) {
        return repository.save(JpaPatron.fromDomain(book)).getId();
    }
    
    @Override
    public void update (UUID id, Patron bookEntity) {
        JpaPatron jpaPatron = repository.findById(id).orElseThrow();
        modelMapper.map(bookEntity, jpaPatron);
        repository.save(jpaPatron);
    }
    
    @Override
    public void delete (UUID id) {
        repository.deleteById(id);
    }
}
