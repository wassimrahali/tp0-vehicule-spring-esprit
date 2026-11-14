package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.MaintenanceDTO;
import tn.esprit.autoloc.service.IMaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenance", description = "Historique de maintenance des véhicules")
public class MaintenanceController {
    private final IMaintenanceService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO create(@Valid @RequestBody MaintenanceDTO dto) { return service.create(dto); }

    @GetMapping
    public List<MaintenanceDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public MaintenanceDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public MaintenanceDTO update(@PathVariable Long id, @Valid @RequestBody MaintenanceDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }

    @GetMapping("/vehicule/{vehiculeId}")
    public List<MaintenanceDTO> historique(@PathVariable Long vehiculeId) {
        return service.findByVehicule(vehiculeId);
    }

    @PostMapping("/vehicule/{vehiculeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO ajouterAuVehicule(@PathVariable Long vehiculeId,
                                            @Valid @RequestBody MaintenanceDTO dto) {
        if (!vehiculeId.equals(dto.vehiculeId())) {
            throw new IllegalArgumentException("L'identifiant de véhicule du chemin et du corps doit correspondre.");
        }
        return service.create(dto);
    }
}
