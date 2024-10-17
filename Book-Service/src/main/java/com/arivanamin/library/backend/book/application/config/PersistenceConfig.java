package com.arivanamin.library.backend.book.application.config;

import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.arivanamin.library.backend.book.infrastructure.BookRepository;
import com.arivanamin.library.backend.book.infrastructure.JpaBookPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PersistenceConfig {
    
    @Bean
    public BookPersistence persistence (BookRepository repository) {
        return new JpaBookPersistence(repository);
    }
}
