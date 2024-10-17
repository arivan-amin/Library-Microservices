package com.arivanamin.library.backend.borrow.domain.command;

import com.arivanamin.library.backend.borrow.domain.exception.BookAlreadyReturnedException;
import com.arivanamin.library.backend.borrow.domain.persistence.BorrowPersistence;
import com.arivanamin.library.backend.core.domain.aspects.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnBookCommand {
    
    private final BorrowPersistence persistence;
    
    @LogExecutionTime
    public void execute (BorrowBookRequest request) {
        persistence.findBorrowedBookRecord(request)
            .ifPresentOrElse(persistence::updateRecordToReturned, () -> {
                throw new BookAlreadyReturnedException();
            });
    }
}
