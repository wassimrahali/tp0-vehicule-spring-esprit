package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EmployeDTO;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@Tag(name = "Employé", description = "Gestion des employés et de leur agence")
public class EmployeController {
    private final IEmployeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDTO create(@Valid @RequestBody EmployeDTO dto) { return service.create(dto); }

    @GetMapping
    public List<EmployeDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public EmployeDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public EmployeDTO update(@PathVariable Long id, @Valid @RequestBody EmployeDTO dto) {
        return service.update(id, dto);
    }

    @PutMapping("/{employeId}/agence/{agenceId}")
    public EmployeDTO affecterAgence(@PathVariable Long employeId, @PathVariable Long agenceId) {
        return service.affecterAgence(employeId, agenceId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
