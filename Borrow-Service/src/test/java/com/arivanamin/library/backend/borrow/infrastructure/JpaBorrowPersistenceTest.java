package com.arivanamin.library.backend.borrow.infrastructure;

import com.arivanamin.library.backend.borrow.BorrowApplication;
import com.arivanamin.library.backend.borrow.domain.command.BorrowBookRequest;
import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith (SpringExtension.class)
@SpringBootTest (classes = BorrowApplication.class)
@Testcontainers
class JpaBorrowPersistenceTest implements BaseUnitTest {
    
    @Container
    public static MySQLContainer<?> mysqlContainer =
        new MySQLContainer<>("mysql:8.0").withDatabaseName("borrow_service")
            .withUsername("root")
            .withPassword("mysql");
    
    @Autowired
    private BorrowRepository repository;
    
    private JpaBorrowPersistence persistence;
    
    @DynamicPropertySource
    static void setDatasourceProperties (DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }
    
    @BeforeEach
    void setUp () {
        persistence = new JpaBorrowPersistence(repository);
    }
    
    @AfterEach
    void tearDown () {
        repository.deleteAll();
    }
    
    @Test
    void shouldCreateBorrowRecord () {
        // Given
        BorrowRecord record = RANDOM.nextObject(BorrowRecord.class);
        record.setId(null);
        
        // When
        persistence.createBorrowRecord(record);
        
        // Then
        List<JpaBorrowRecord> records = repository.findAll();
        assertEquals(1, records.size());
        JpaBorrowRecord savedRecord = records.getFirst();
        assertEquals(record.getBookId(), savedRecord.getBookId());
        assertEquals(record.getPatronId(), savedRecord.getPatronId());
    }
    
    @Test
    void shouldUpdateRecordToReturned () {
        // Given
        BorrowRecord record = RANDOM.nextObject(BorrowRecord.class);
        record.setReturnDateTime(null);
        repository.save(JpaBorrowRecord.fromDomain(record));
        
        // When
        persistence.updateRecordToReturned(record);
        
        // Then
        List<JpaBorrowRecord> records = repository.findAll();
        assertEquals(1, records.size());
        JpaBorrowRecord updatedRecord = records.getFirst();
        assertNotNull(updatedRecord.getReturnDateTime());
    }
    
    @Test
    void shouldFindBorrowedBookRecord () {
        // Given
        UUID bookId = UUID.randomUUID();
        UUID patronId = UUID.randomUUID();
        BorrowRecord record = new BorrowRecord(null, bookId, patronId, LocalDateTime.now(), null);
        repository.save(JpaBorrowRecord.fromDomain(record));
        BorrowBookRequest request = new BorrowBookRequest(bookId, patronId);
        
        // When
        Optional<BorrowRecord> foundRecord = persistence.findBorrowedBookRecord(request);
        
        // Then
        assertTrue(foundRecord.isPresent());
        assertEquals(bookId, foundRecord.get().getBookId());
        assertEquals(patronId, foundRecord.get().getPatronId());
    }
    
    @Test
    void shouldReturnEmptyWhenNoBorrowedBookRecordFound () {
        // Given
        BorrowBookRequest request = new BorrowBookRequest(UUID.randomUUID(), UUID.randomUUID());
        
        // When
        Optional<BorrowRecord> foundRecord = persistence.findBorrowedBookRecord(request);
        
        // Then
        assertFalse(foundRecord.isPresent());
    }
}

