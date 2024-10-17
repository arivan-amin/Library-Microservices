package com.arivanamin.library.backend.patron.application.cli;

import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.persistence.PatronPersistence;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
class DemoDataInitializer {
    
    Faker faker = new Faker();
    
    @Bean
    CommandLineRunner initDatabase (PatronPersistence persistence) {
        return args -> {
            if (persistence.findAll().isEmpty()) {
                int numberOfEntities = faker.number().numberBetween(5, 15);
                populatePatronRepository(persistence, numberOfEntities);
            }
        };
    }
    
    private void populatePatronRepository (PatronPersistence persistence, int numberOfEntities) {
        IntStream.rangeClosed(1, numberOfEntities)
            .mapToObj(this::createPatron)
            .forEachOrdered(persistence::create);
    }
    
    private Patron createPatron (int i) {
        Patron Patron = new Patron();
        Patron.setName(faker.commerce().productName());
        Patron.setContactInformation(faker.address().fullAddress());
        return Patron;
    }
}
