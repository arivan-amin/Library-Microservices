package com.arivanamin.library.backend.patron.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatronRepository extends JpaRepository<JpaPatron, UUID> {
    
}
