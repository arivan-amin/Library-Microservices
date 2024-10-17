package com.arivanamin.library.backend.borrow.infrastructure;

import com.arivanamin.library.backend.borrow.domain.command.BorrowBookRequest;
import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;
import com.arivanamin.library.backend.borrow.domain.persistence.BorrowPersistence;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Transactional
public class JpaBorrowPersistence implements BorrowPersistence {
    
    private final BorrowRepository repository;
    
    @Override
    public void createBorrowRecord (BorrowRecord record) {
        repository.save(JpaBorrowRecord.fromDomain(record));
    }
    
    @Override
    public void updateRecordToReturned (BorrowRecord record) {
        repository.findByBookIdAndPatronIdAndReturnDateTimeIsNull(record.getBookId(),
            record.getPatronId()).ifPresent(jpaBorrowRecord -> {
            jpaBorrowRecord.setReturnDateTime(LocalDateTime.now());
            repository.save(jpaBorrowRecord);
        });
    }
    
    @Override
    public Optional<BorrowRecord> findBorrowedBookRecord (BorrowBookRequest request) {
        Optional<JpaBorrowRecord> jpaOptional =
            repository.findByBookIdAndPatronIdAndReturnDateTimeIsNull(request.getBookId(),
                request.getPatronId());
        return jpaOptional.map(JpaBorrowRecord::toDomain);
    }
}
