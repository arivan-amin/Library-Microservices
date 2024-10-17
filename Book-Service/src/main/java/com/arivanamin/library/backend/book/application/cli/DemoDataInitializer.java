package com.arivanamin.library.backend.book.application.cli;

import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.persistence.BookPersistence;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
class DemoDataInitializer {
    
    Faker faker = new Faker();
    
    @Bean
    CommandLineRunner initDatabase (BookPersistence persistence) {
        return args -> {
            if (persistence.findAll().isEmpty()) {
                int numberOfEntities = faker.number().numberBetween(5, 15);
                populateBookRepository(persistence, numberOfEntities);
            }
        };
    }
    
    private void populateBookRepository (BookPersistence persistence, int numberOfEntities) {
        IntStream.rangeClosed(1, numberOfEntities)
            .mapToObj(this::createBook)
            .forEachOrdered(persistence::create);
    }
    
    private Book createBook (int i) {
        Book book = new Book();
        book.setIsbn(faker.numerify("###-##-#####-###"));
        book.setTitle(faker.book().title());
        book.setAuthor(faker.book().author());
        book.setPublicationYear(faker.elderScrolls().firstName());
        book.setPublicationYear(createFakePublicationYear());
        return book;
    }
    
    private String createFakePublicationYear () {
        return String.valueOf(
            LocalDate.now().minusYears(faker.number().numberBetween(1, 20)).getYear());
    }
}
