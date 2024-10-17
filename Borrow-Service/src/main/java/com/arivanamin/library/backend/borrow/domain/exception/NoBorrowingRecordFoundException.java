package com.arivanamin.library.backend.borrow.domain.exception;

public class NoBorrowingRecordFoundException extends RuntimeException {
    
    public NoBorrowingRecordFoundException () {
        super("No borrowing record was found for the requested book and patron");
    }
}
