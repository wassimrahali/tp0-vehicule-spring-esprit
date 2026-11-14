package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ClientDTO;
import tn.esprit.autoloc.service.IClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client", description = "Gestion des clients")
public class ClientController {
    private final IClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO create(@Valid @RequestBody ClientDTO dto) { return service.create(dto); }

    @GetMapping
    public List<ClientDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ClientDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public ClientDTO update(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
