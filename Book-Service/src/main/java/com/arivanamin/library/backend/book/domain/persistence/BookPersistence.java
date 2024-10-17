package com.arivanamin.library.backend.book.domain.persistence;

import com.arivanamin.library.backend.book.domain.entity.Book;

import java.util.*;

public interface BookPersistence {
    
    List<Book> findAll ();
    
    Optional<Book> findById (UUID id);
    
    UUID create (Book book);
    
    void update (UUID id, Book book);
    
    void delete (UUID id);
}
