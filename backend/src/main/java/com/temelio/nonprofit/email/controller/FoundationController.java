package com.temelio.nonprofit.email.controller;

import com.temelio.nonprofit.email.dto.FoundationChangeDTO;
import com.temelio.nonprofit.email.dto.FoundationDTO;
import com.temelio.nonprofit.email.dto.Response;
import com.temelio.nonprofit.email.service.FoundationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/foundations")
@Tag(name = "Foundations")
public class FoundationController {

    private final FoundationService foundationService;

    @Autowired
    public FoundationController(FoundationService foundationService) {
        this.foundationService = foundationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FoundationDTO>> getFoundationById(@PathVariable UUID id) {
        FoundationDTO foundationDTO = foundationService.findById(id);

        Response<FoundationDTO> response = new Response<>(HttpStatus.OK.value(), "OK", foundationDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Response<FoundationDTO>> getFoundationByEmail(@PathVariable String email) {
        FoundationDTO foundationDTO = foundationService.findByEmail(email);

        Response<FoundationDTO> response = new Response<>(HttpStatus.OK.value(), "OK", foundationDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<FoundationDTO>>> getAllFoundations() {
        List<FoundationDTO> foundations = foundationService.findAll();
        Response<List<FoundationDTO>> response = new Response<>(HttpStatus.OK.value(), "OK", foundations);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<FoundationDTO>> saveFoundation(@RequestBody FoundationChangeDTO foundationDTO) {
        FoundationDTO newFoundation = foundationService.save(foundationDTO);

        Response<FoundationDTO> response = new Response<>(HttpStatus.CREATED.value(), "Foundation created!", newFoundation);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<FoundationDTO>> updateFoundation(@PathVariable UUID id, @RequestBody FoundationChangeDTO foundationDTO) {
        FoundationDTO updatedFoundation = foundationService.update(id, foundationDTO);

        Response<FoundationDTO> response = new Response<>(HttpStatus.OK.value(), "Foundation updated!", updatedFoundation);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteFoundation(@PathVariable UUID id) {
        foundationService.deleteById(id);

        Response<Void> response = new Response<>(HttpStatus.NO_CONTENT.value(), "Foundation deleted!", null);
        return ResponseEntity.ok(response);
    }
}
