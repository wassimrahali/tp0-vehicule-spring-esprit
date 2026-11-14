package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiement", description = "Gestion des paiements")
public class PaiementController {
    private final IPaiementService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDTO create(@Valid @RequestBody PaiementDTO dto) { return service.create(dto); }

    @GetMapping
    public List<PaiementDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public PaiementDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public PaiementDTO update(@PathVariable Long id, @Valid @RequestBody PaiementDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
