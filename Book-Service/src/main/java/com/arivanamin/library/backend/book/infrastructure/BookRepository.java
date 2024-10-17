package com.arivanamin.library.backend.book.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<JpaBook, UUID> {
    
}
