package tn.esprit.autoloc.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.dto.EmployeDTO;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@Tag(name = "Agence", description = "Gestion des agences, véhicules et employés rattachés")
public class AgenceController {
    private final IAgenceService service;
    private final AgenceMapper mapper;
    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;
    private final IEmployeService employeService;
    private final EmployeMapper employeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDTO create(@Valid @RequestBody AgenceDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping
    public List<AgenceDTO> findAll() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public AgenceDTO findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @PutMapping("/{id}")
    public AgenceDTO update(@PathVariable Long id, @Valid @RequestBody AgenceDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{agenceId}/vehicules")
    public List<VehiculeDTO> vehicules(@PathVariable Long agenceId) {
        return vehiculeService.findByAgence(agenceId).stream().map(vehiculeMapper::toDto).toList();
    }

    @PostMapping("/{agenceId}/vehicules/{vehiculeId}")
    public VehiculeDTO affecterVehicule(@PathVariable Long agenceId, @PathVariable Long vehiculeId) {
        return vehiculeMapper.toDto(vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId));
    }

    @DeleteMapping("/{agenceId}/vehicules/{vehiculeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirerVehicule(@PathVariable Long agenceId, @PathVariable Long vehiculeId) {
        vehiculeService.retirerDeAgence(agenceId, vehiculeId);
    }

    @GetMapping("/{agenceId}/employes")
    public List<EmployeDTO> employes(@PathVariable Long agenceId) {
        return employeService.findByAgence(agenceId).stream().map(employeMapper::toDto).toList();
    }

    @PostMapping("/{agenceId}/employes/{employeId}")
    public EmployeDTO affecterEmploye(@PathVariable Long agenceId, @PathVariable Long employeId) {
        return employeMapper.toDto(employeService.affecterAgence(employeId, agenceId));
    }
}
