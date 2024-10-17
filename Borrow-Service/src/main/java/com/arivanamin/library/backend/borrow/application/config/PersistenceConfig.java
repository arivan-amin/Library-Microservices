package com.arivanamin.library.backend.borrow.application.config;

import com.arivanamin.library.backend.borrow.domain.persistence.BorrowPersistence;
import com.arivanamin.library.backend.borrow.infrastructure.BorrowRepository;
import com.arivanamin.library.backend.borrow.infrastructure.JpaBorrowPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PersistenceConfig {
    
    @Bean
    public BorrowPersistence persistence (BorrowRepository repository) {
        return new JpaBorrowPersistence(repository);
    }
}
