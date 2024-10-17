package com.arivanamin.library.backend.borrow.domain.command;

import lombok.Value;

import java.util.UUID;

@Value
public class BorrowBookRequest {
    
    UUID bookId;
    UUID patronId;
    
    public static BorrowBookRequest of (UUID bookId, UUID patronId) {
        return new BorrowBookRequest(bookId, patronId);
    }
}
