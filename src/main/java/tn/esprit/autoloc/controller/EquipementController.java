package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipement", description = "Gestion des équipements")
public class EquipementController {
    private final IEquipementService service;
    private final IVehiculeService vehiculeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(@Valid @RequestBody EquipementDTO dto) { return service.create(dto); }

    @GetMapping
    public List<EquipementDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public EquipementDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public EquipementDTO update(@PathVariable Long id, @Valid @RequestBody EquipementDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }

    @GetMapping("/{id}/vehicules")
    public List<VehiculeDTO> vehicules(@PathVariable Long id) { return vehiculeService.findByEquipement(id); }
}
