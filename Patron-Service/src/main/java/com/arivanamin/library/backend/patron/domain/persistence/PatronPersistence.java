package com.arivanamin.library.backend.patron.domain.persistence;

import com.arivanamin.library.backend.patron.domain.entity.Patron;

import java.util.*;

public interface PatronPersistence {
    
    List<Patron> findAll ();
    
    Optional<Patron> findById (UUID id);
    
    UUID create (Patron Patron);
    
    void update (UUID id, Patron Patron);
    
    void delete (UUID id);
}
