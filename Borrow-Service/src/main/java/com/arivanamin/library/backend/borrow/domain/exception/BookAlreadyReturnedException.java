package com.arivanamin.library.backend.borrow.domain.exception;

public class BookAlreadyReturnedException extends RuntimeException {
    
    public BookAlreadyReturnedException () {
        super("Requested Book is already returned");
    }
}
