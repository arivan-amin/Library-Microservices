package com.arivanamin.library.backend.book.infrastructure;

import com.arivanamin.library.backend.book.BookApplication;
import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
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
@SpringBootTest (classes = BookApplication.class)
@Testcontainers
class JpaBookPersistenceTest implements BaseUnitTest {
    
    @Container
    public static MySQLContainer<?> mysqlContainer =
        new MySQLContainer<>("mysql:8.0").withDatabaseName("book_service")
            .withUsername("root")
            .withPassword("mysql");
    
    @Autowired
    private BookRepository repository;
    
    private JpaBookPersistence persistence;
    
    @DynamicPropertySource
    static void setDatasourceProperties (DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
    
    @BeforeEach
    void setUp () {
        persistence = new JpaBookPersistence(repository);
    }
    
    @AfterEach
    void tearDown () {
        repository.deleteAll();
    }
    
    @Test
    void shouldReturnAllBooks () {
        // Given
        Book book1 = RANDOM.nextObject(Book.class);
        Book book2 = RANDOM.nextObject(Book.class);
        repository.save(JpaBook.fromDomain(book1));
        repository.save(JpaBook.fromDomain(book2));
        
        // When
        List<Book> books = persistence.findAll();
        
        // Then
        assertEquals(2, books.size());
    }
    
    @Test
    void shouldReturnBookById () {
        // Given
        String title = FAKER.zelda().character();
        Book book = RANDOM.nextObject(Book.class);
        book.setTitle(title);
        UUID bookId = repository.save(JpaBook.fromDomain(book)).getId();
        
        // When
        Optional<Book> foundBook = persistence.findById(bookId);
        
        // Then
        assertTrue(foundBook.isPresent());
        assertEquals(title, foundBook.get().getTitle());
    }
    
    @Test
    void shouldCreateBook () {
        // Given
        Book book = RANDOM.nextObject(Book.class);
        
        // When
        UUID bookId = persistence.create(book);
        
        // Then
        Optional<JpaBook> savedBook = repository.findById(bookId);
        assertTrue(savedBook.isPresent());
    }
    
    @Test
    void shouldUpdateBook () {
        // Given
        Book book = new Book(null, "3209482309842", "alpha", "bravo", "delta");
        UUID bookId = repository.save(JpaBook.fromDomain(book)).getId();
        Book updatedBook = new Book(bookId, "203402389430", "charlie", "echo", "hotel");
        
        // When
        persistence.update(bookId, updatedBook);
        
        // Then
        Optional<JpaBook> savedBook = repository.findById(bookId);
        assertTrue(savedBook.isPresent());
        assertEquals(bookId, savedBook.get().getId());
        assertNotEquals(book.getIsbn(), savedBook.get().getIsbn());
        assertNotEquals(book.getTitle(), savedBook.get().getTitle());
        assertNotEquals(book.getAuthor(), savedBook.get().getAuthor());
        assertNotEquals(book.getPublicationYear(), savedBook.get().getPublicationYear());
    }
    
    @Test
    void shouldDeleteBook () {
        // Given
        Book book = RANDOM.nextObject(Book.class);
        UUID bookId = repository.save(JpaBook.fromDomain(book)).getId();
        
        // When
        persistence.delete(bookId);
        
        // Then
        Optional<JpaBook> deletedBook = repository.findById(bookId);
        assertFalse(deletedBook.isPresent());
    }
}
