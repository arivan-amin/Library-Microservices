package com.arivanamin.library.backend.book.infrastructure;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class JpaBookPersistence implements BookPersistence {
    
    private final BookRepository repository;
    
    ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public List<Book> findAll () {
        return repository.findAll().stream().map(JpaBook::toDomain).toList();
    }
    
    @Override
    public Optional<Book> findById (UUID id) {
        return repository.findById(id).map(JpaBook::toDomain);
    }
    
    @Override
    public UUID create (Book book) {
        return repository.save(JpaBook.fromDomain(book)).getId();
    }
    
    @Override
    public void update (UUID id, Book bookEntity) {
        JpaBook jpaBook = repository.findById(id).orElseThrow();
        modelMapper.map(bookEntity, jpaBook);
        repository.save(jpaBook);
    }
    
    @Override
    public void delete (UUID id) {
        repository.deleteById(id);
    }
}
