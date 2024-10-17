package com.arivanamin.library.backend.borrow.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecord {
    
    private UUID id;
    private UUID bookId;
    private UUID patronId;
    private LocalDateTime borrowDateTime;
    private LocalDateTime returnDateTime;
}
