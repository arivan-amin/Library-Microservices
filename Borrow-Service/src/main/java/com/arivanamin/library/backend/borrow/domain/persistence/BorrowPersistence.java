package com.arivanamin.library.backend.borrow.domain.persistence;

import com.arivanamin.library.backend.borrow.domain.command.BorrowBookRequest;
import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;

import java.util.*;

public interface BorrowPersistence {
    
    void createBorrowRecord (BorrowRecord book);
    
    void updateRecordToReturned (BorrowRecord book);
    
    Optional<BorrowRecord> findBorrowedBookRecord (BorrowBookRequest request);
}
