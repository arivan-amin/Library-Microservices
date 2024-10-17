package com.arivanamin.library.backend.borrow.domain.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    
    public BookAlreadyBorrowedException () {
        super("Requested Book is already borrowed");
    }
}
