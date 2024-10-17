package com.arivanamin.library.backend.patron.application.endpoints;

import com.arivanamin.library.backend.patron.domain.command.*;
import com.arivanamin.library.backend.patron.domain.query.ReadPatronByIdQuery;
import com.arivanamin.library.backend.patron.domain.query.ReadPatronsQuery;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.Cacheable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping ("/api/patrons")
@RequiredArgsConstructor
@Cacheable
@Slf4j
public class PatronController {
    
    private final ReadPatronsQuery readQuery;
    private final ReadPatronByIdQuery readByIdQuery;
    private final CreatePatronCommand createCommand;
    private final UpdatePatronCommand updateCommand;
    private final DeletePatronCommand deleteCommand;
    
    @GetMapping ("")
    @Operation (summary = "Get a list of all patron profiles")
    @ResponseStatus (HttpStatus.OK)
    public ReadPatronsResponse getAllPatrons () {
        return ReadPatronsResponse.of(readQuery.execute());
    }
    
    @GetMapping ("/{id}")
    @Operation (summary = "Get a single patron by id")
    @ResponseStatus (HttpStatus.OK)
    public PatronResponse getPatronById (@PathVariable UUID id) {
        return PatronResponse.of(readByIdQuery.execute(id));
    }
    
    @PostMapping ("")
    @Operation (summary = "Creates a patron profile")
    @ResponseStatus (HttpStatus.CREATED)
    public CreatePatronResponse createPatron (@RequestBody CreatePatronRequest request) {
        UUID createdPatronId = createCommand.execute(request.toDomain());
        return CreatePatronResponse.of(createdPatronId);
    }
    
    @PutMapping ("/{id}")
    @Operation (summary = "Updates a patron profile")
    @ResponseStatus (HttpStatus.OK)
    public void updatePatron (@PathVariable UUID id, @RequestBody CreatePatronRequest request) {
        updateCommand.execute(id, request.toDomain());
    }
    
    @DeleteMapping ("/{id}")
    @Operation (summary = "Deletes a patron profile")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void deletePatron (@PathVariable UUID id) {
        deleteCommand.execute(id);
    }
}
