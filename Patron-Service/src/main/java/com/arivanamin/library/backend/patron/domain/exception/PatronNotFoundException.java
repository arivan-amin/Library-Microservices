package com.arivanamin.library.backend.patron.domain.exception;

public class PatronNotFoundException extends RuntimeException {
    
    public PatronNotFoundException () {
        super("Patron by the requested id not found");
    }
}
