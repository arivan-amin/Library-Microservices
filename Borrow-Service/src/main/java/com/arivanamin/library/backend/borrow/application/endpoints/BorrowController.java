package com.arivanamin.library.backend.borrow.application.endpoints;

import com.arivanamin.library.backend.borrow.domain.command.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Cacheable
@Slf4j
public class BorrowController {
    
    private final BorrowBookCommand borrowCommand;
    private final ReturnBookCommand returnCommand;
    
    @PostMapping ("/api/borrow/{bookId}/patron/{patronId}")
    @Operation (summary = "Creates a borrow record for book and patron")
    @ResponseStatus (HttpStatus.CREATED)
    public void createBook (@PathVariable UUID bookId, @PathVariable UUID patronId) {
        borrowCommand.execute(BorrowBookRequest.of(bookId, patronId));
    }
    
    @PutMapping ("/api/borrow/{bookId}/patron/{patronId}")
    @Operation (summary = "Marks borrow record as returned")
    @ResponseStatus (HttpStatus.OK)
    public void updateBook (@PathVariable UUID bookId, @PathVariable UUID patronId) {
        returnCommand.execute(BorrowBookRequest.of(bookId, patronId));
    }
}
