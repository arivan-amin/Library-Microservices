package com.arivanamin.library.backend.book.domain.exception;

public class BookNotFoundException extends RuntimeException {
    
    public BookNotFoundException () {
        super("book by the requested id not found");
    }
}
