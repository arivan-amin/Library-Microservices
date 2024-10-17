package com.arivanamin.library.backend.borrow.application.endpoints;

import com.arivanamin.library.backend.borrow.domain.command.*;
import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (value = BorrowController.class)
@AutoConfigureMockMvc
class BorrowControllerTest implements BaseUnitTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private BorrowBookCommand borrowCommand;
    
    @MockBean
    private ReturnBookCommand returnCommand;
    
    @Test
    void shouldCreateBorrowRecord () throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        UUID patronId = UUID.randomUUID();
        BorrowBookRequest request = BorrowBookRequest.of(bookId, patronId);
        
        doNothing().when(borrowCommand).execute(request);
        
        // When & Then
        mockMvc.perform(
            post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId).contentType(
                APPLICATION_JSON)).andExpect(status().isCreated());
        
        // Verify the command was executed
        verify(borrowCommand).execute(request);
    }
    
    @Test
    void shouldMarkBookAsReturned () throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        UUID patronId = UUID.randomUUID();
        BorrowBookRequest request = BorrowBookRequest.of(bookId, patronId);
        
        doNothing().when(returnCommand).execute(request);
        
        // When & Then
        mockMvc.perform(put("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId).contentType(
            APPLICATION_JSON)).andExpect(status().isOk());
        
        // Verify the command was executed
        verify(returnCommand).execute(request);
    }
}
