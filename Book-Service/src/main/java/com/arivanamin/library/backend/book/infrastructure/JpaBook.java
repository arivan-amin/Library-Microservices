package com.arivanamin.library.backend.book.infrastructure;

import com.arivanamin.library.backend.book.domain.entity.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Entity
@Table (name = "Books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
class JpaBook {
    
    private static ModelMapper mapper = new ModelMapper();
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    UUID id;
    
    @NotBlank
    @Pattern (regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$",
        message = "Must be a valid ISBN")
    @Column
    private String isbn;
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String author;
    
    @NotBlank
    @Digits (integer = 4, fraction = 0, message = "Provide a valid year")
    private String publicationYear;
    
    public static JpaBook fromDomain (Book book) {
        return mapper.map(book, JpaBook.class);
    }
    
    public Book toDomain () {
        return mapper.map(this, Book.class);
    }
}
