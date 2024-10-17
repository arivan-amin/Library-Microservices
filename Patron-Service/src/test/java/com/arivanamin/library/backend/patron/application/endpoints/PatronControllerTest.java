package com.arivanamin.library.backend.patron.application.endpoints;

import com.arivanamin.library.backend.core.domain.testing.BaseUnitTest;
import com.arivanamin.library.backend.patron.domain.command.*;
import com.arivanamin.library.backend.patron.domain.entity.Patron;
import com.arivanamin.library.backend.patron.domain.query.ReadPatronByIdQuery;
import com.arivanamin.library.backend.patron.domain.query.ReadPatronsQuery;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (value = PatronController.class)
@AutoConfigureMockMvc
class PatronControllerTest implements BaseUnitTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private ReadPatronsQuery readQuery;
    
    @MockBean
    private ReadPatronByIdQuery readByIdQuery;
    
    @MockBean
    private CreatePatronCommand createCommand;
    
    @MockBean
    private UpdatePatronCommand updateCommand;
    
    @MockBean
    private DeletePatronCommand deleteCommand;
    
    @Test
    void shouldReturnAllPatrons () throws Exception {
        // Given
        List<Patron> patrons = List.of(RANDOM.nextObject(Patron.class));
        when(readQuery.execute()).thenReturn(patrons);
        
        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/patrons").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        
        // Then
        String content = mvcResult.getResponse().getContentAsString();
        ReadPatronsResponse response = mapper.readValue(content, ReadPatronsResponse.class);
        assertThat(response.responses).hasSize(1);
        verify(readQuery, times(1)).execute();
    }
    
    @Test
    void shouldReturnPatronById () throws Exception {
        // Given
        UUID patronId = UUID.randomUUID();
        Patron patron = RANDOM.nextObject(Patron.class);
        patron.setId(patronId);
        when(readByIdQuery.execute(patronId)).thenReturn(patron);
        
        // When
        MvcResult mvcResult =
            mockMvc.perform(get("/api/patrons/{id}", patronId).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        
        // Then
        String content = mvcResult.getResponse().getContentAsString();
        PatronResponse response = mapper.readValue(content, PatronResponse.class);
        assertThat(response).isEqualTo(PatronResponse.of(patron));
        verify(readByIdQuery, times(1)).execute(patronId);
    }
    
    @Test
    void shouldCreatePatron () throws Exception {
        // Given
        UUID patronId = UUID.randomUUID();
        CreatePatronRequest request = RANDOM.nextObject(CreatePatronRequest.class);
        when(createCommand.execute(any(Patron.class))).thenReturn(patronId);
        
        // When
        MvcResult mvcResult = mockMvc.perform(post("/api/patrons").contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();
        
        // Then
        String content = mvcResult.getResponse().getContentAsString();
        CreatePatronResponse response = mapper.readValue(content, CreatePatronResponse.class);
        assertThat(response.getId()).isEqualTo(patronId);
        verify(createCommand, times(1)).execute(any(Patron.class));
    }
    
    @Test
    void shouldUpdatePatron () throws Exception {
        // Given
        UUID patronId = UUID.randomUUID();
        CreatePatronRequest request = RANDOM.nextObject(CreatePatronRequest.class);
        
        // When
        mockMvc.perform(put("/api/patrons/{id}", patronId).contentType(APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))).andExpect(status().isOk());
        
        // Then
        verify(updateCommand, times(1)).execute(eq(patronId), any(Patron.class));
    }
    
    @Test
    void shouldDeletePatron () throws Exception {
        // Given
        UUID patronId = UUID.randomUUID();
        
        // When
        mockMvc.perform(delete("/api/patrons/{id}", patronId).contentType(APPLICATION_JSON))
            .andExpect(status().isNoContent());
        
        // Then
        verify(deleteCommand, times(1)).execute(patronId);
    }
}
