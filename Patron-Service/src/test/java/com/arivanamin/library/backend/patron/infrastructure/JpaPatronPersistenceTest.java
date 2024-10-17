package com.arivanamin.library.backend.patron.infrastructure;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.PatronApplication;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (SpringExtension.class)
@SpringBootTest (classes = PatronApplication.class)
@Testcontainers
class JpaPatronPersistenceTest implements BaseUnitTest {
    
    @Container
    public static MySQLContainer<?> mysqlContainer =
        new MySQLContainer<>("mysql:8.0").withDatabaseName("patron_service")
            .withUsername("root")
            .withPassword("mysql");
    
    @Autowired
    private PatronRepository repository;
    
    private JpaPatronPersistence persistence;
    
    @DynamicPropertySource
    static void setDatasourceProperties (DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
    
    @BeforeEach
    void setUp () {
        persistence = new JpaPatronPersistence(repository);
    }
    
    @AfterEach
    void tearDown () {
        repository.deleteAll();
    }
    
    @Test
    void shouldReturnAllPatrons () {
        // Given
        Patron patron1 = RANDOM.nextObject(Patron.class);
        Patron patron2 = RANDOM.nextObject(Patron.class);
        repository.save(JpaPatron.fromDomain(patron1));
        repository.save(JpaPatron.fromDomain(patron2));
        
        // When
        List<Patron> patrons = persistence.findAll();
        
        // Then
        assertEquals(2, patrons.size());
    }
    
    @Test
    void shouldReturnPatronById () {
        // Given
        String name = FAKER.zelda().character();
        Patron patron = RANDOM.nextObject(Patron.class);
        patron.setName(name);
        UUID patronId = repository.save(JpaPatron.fromDomain(patron)).getId();
        
        // When
        Optional<Patron> foundPatron = persistence.findById(patronId);
        
        // Then
        assertTrue(foundPatron.isPresent());
        assertEquals(name, foundPatron.get().getName());
    }
    
    @Test
    void shouldCreatePatron () {
        // Given
        Patron patron = RANDOM.nextObject(Patron.class);
        
        // When
        UUID patronId = persistence.create(patron);
        
        // Then
        Optional<JpaPatron> savedPatron = repository.findById(patronId);
        assertTrue(savedPatron.isPresent());
    }
    
    @Test
    void shouldUpdatePatron () {
        // Given
        Patron patron = new Patron(null, "alpha", "bravo");
        UUID patronId = repository.save(JpaPatron.fromDomain(patron)).getId();
        Patron updatedPatron = new Patron(patronId, "charlie", "echo");
        
        // When
        persistence.update(patronId, updatedPatron);
        
        // Then
        Optional<JpaPatron> savedPatron = repository.findById(patronId);
        assertTrue(savedPatron.isPresent());
        assertEquals(patronId, savedPatron.get().getId());
        assertNotEquals(patron.getName(), savedPatron.get().getName());
        assertNotEquals(patron.getContactInformation(), savedPatron.get().getContactInformation());
    }
    
    @Test
    void shouldDeletePatron () {
        // Given
        Patron patron = RANDOM.nextObject(Patron.class);
        UUID patronId = repository.save(JpaPatron.fromDomain(patron)).getId();
        
        // When
        persistence.delete(patronId);
        
        // Then
        Optional<JpaPatron> deletedPatron = repository.findById(patronId);
        assertFalse(deletedPatron.isPresent());
    }
}
