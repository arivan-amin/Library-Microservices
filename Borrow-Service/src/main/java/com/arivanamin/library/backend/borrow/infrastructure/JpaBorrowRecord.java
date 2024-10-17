package com.arivanamin.library.backend.borrow.infrastructure;

import com.arivanamin.library.backend.borrow.domain.entity.BorrowRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "Borrows")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
class JpaBorrowRecord {
    
    private static ModelMapper mapper = new ModelMapper();
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    UUID id;
    
    @NotNull
    private UUID bookId;
    
    @NotNull
    private UUID patronId;
    
    @NotNull
    private LocalDateTime borrowDateTime;
    
    private LocalDateTime returnDateTime;
    
    public static JpaBorrowRecord fromDomain (BorrowRecord record) {
        return mapper.map(record, JpaBorrowRecord.class);
    }
    
    public BorrowRecord toDomain () {
        return mapper.map(this, BorrowRecord.class);
    }
}
