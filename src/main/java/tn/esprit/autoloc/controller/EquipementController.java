package tn.esprit.autoloc.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipement", description = "Gestion des équipements et des véhicules associés")
public class EquipementController {
    private final IEquipementService service;
    private final EquipementMapper mapper;
    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(@Valid @RequestBody EquipementDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping
    public List<EquipementDTO> findAll() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public EquipementDTO findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @PutMapping("/{id}")
    public EquipementDTO update(@PathVariable Long id, @Valid @RequestBody EquipementDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/{id}/vehicules")
    public List<VehiculeDTO> vehicules(@PathVariable Long id) {
        return vehiculeService.findByEquipement(id).stream().map(vehiculeMapper::toDto).toList();
    }
}
