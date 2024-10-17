package com.arivanamin.library.backend.book.application.endpoints;

import com.arivanamin.library.backend.book.domain.command.*;
import com.arivanamin.library.backend.book.domain.entity.Book;
import com.arivanamin.library.backend.book.domain.query.ReadBookByIdQuery;
import com.arivanamin.library.backend.book.domain.query.ReadBooksQuery;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (value = BookController.class)
@AutoConfigureMockMvc
class BookControllerTest implements BaseUnitTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private ReadBooksQuery readQuery;
    
    @MockBean
    private ReadBookByIdQuery readByIdQuery;
    
    @MockBean
    private CreateBookCommand createCommand;
    
    @MockBean
    private UpdateBookCommand updateCommand;
    
    @MockBean
    private DeleteBookCommand deleteCommand;
    
    @Test
    void shouldReturnAllBooks () throws Exception {
        // Given
        List<Book> books = List.of(RANDOM.nextObject(Book.class));
        when(readQuery.execute()).thenReturn(books);
        
        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/books").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        
        // Then
        String content = mvcResult.getResponse().getContentAsString();
        ReadBooksResponse response = mapper.readValue(content, ReadBooksResponse.class);
        assertThat(response.responses).hasSize(1);
        verify(readQuery, times(1)).execute();
    }
    
    @Test
    void shouldReturnBookById () throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        Book book = RANDOM.nextObject(Book.class);
        book.setId(bookId);
        when(readByIdQuery.execute(bookId)).thenReturn(book);
        
        // When
        MvcResult mvcResult =
            mockMvc.perform(get("/api/books/{id}", bookId).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // Then
        String content = mvcResult.getResponse().getContentAsString();
        BookResponse response = mapper.readValue(content, BookResponse.class);
        assertThat(response).isEqualTo(BookResponse.of(book));
        verify(readByIdQuery, times(1)).execute(bookId);
    }
    
    @Test
    void shouldCreateBook () throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        CreateBookRequest request = RANDOM.nextObject(CreateBookRequest.class);
        when(createCommand.execute(any(Book.class))).thenReturn(bookId);
        
        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();
        
        // Then
        String content = mvcResult.getResponse().getContentAsString();
        CreateBookResponse response = mapper.readValue(content, CreateBookResponse.class);
        assertThat(response.getId()).isEqualTo(bookId);
        verify(createCommand, times(1)).execute(any(Book.class));
    }
    
    @Test
    void shouldUpdateBook () throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        CreateBookRequest request = RANDOM.nextObject(CreateBookRequest.class);
        
        // When
        mockMvc.perform(put("/api/books/{id}", bookId).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))).andExpect(status().isOk());
        
        // Then
        verify(updateCommand, times(1)).execute(eq(bookId), any(Book.class));
    }
    
    @Test
    void shouldDeleteBook () throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        
        // When
        mockMvc.perform(delete("/api/books/{id}", bookId).contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        // Then
        verify(deleteCommand, times(1)).execute(bookId);
    }
}
