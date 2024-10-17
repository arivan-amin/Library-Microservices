package com.arivanamin.library.backend.patron.application.config;

import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import com.arivanamin.library.backend.patron.infrastructure.JpaPatronPersistence;
import com.arivanamin.library.backend.patron.infrastructure.PatronRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PersistenceConfig {
    
    @Bean
    public PatronPersistence persistence (PatronRepository repository) {
        return new JpaPatronPersistence(repository);
    }
}
