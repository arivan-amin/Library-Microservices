package com.arivanamin.library.backend.borrow.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BorrowRepository extends JpaRepository<JpaBorrowRecord, UUID> {
    
    Optional<JpaBorrowRecord> findByBookIdAndPatronIdAndReturnDateTimeIsNull (UUID bookId,
                                                                           UUID patronId);
}
