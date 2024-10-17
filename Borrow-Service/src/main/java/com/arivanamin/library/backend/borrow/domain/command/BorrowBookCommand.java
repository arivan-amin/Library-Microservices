package com.arivanamin.library.backend.borrow.domain.command;

import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;
import com.arivanamin.library.backend.borrow.domain.exception.BookAlreadyBorrowedException;
import com.arivanamin.library.backend.borrow.domain.persistence.BorrowPersistence;
import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BorrowBookCommand {
    
    private final BorrowPersistence persistence;
    
    @LogExecutionTime
    public void execute (BorrowBookRequest request) {
        if (persistence.findBorrowedBookRecord(request).isPresent()) {
            throw new BookAlreadyBorrowedException();
        }
        BorrowRecord record = mapRequestToBorrowRecord(request);
        persistence.createBorrowRecord(record);
    }
    
    public BorrowRecord mapRequestToBorrowRecord (BorrowBookRequest request) {
        BorrowRecord record = new BorrowRecord();
        record.setBookId(request.getBookId());
        record.setPatronId(request.getPatronId());
        record.setBorrowDateTime(LocalDateTime.now());
        return record;
    }
}
