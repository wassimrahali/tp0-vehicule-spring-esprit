package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.*;
import tn.esprit.autoloc.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicule", description = "Gestion des véhicules et associations")
public class VehiculeController {
    private final IVehiculeService service;
    private final IMaintenanceService maintenanceService;
    private final IReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO create(@Valid @RequestBody VehiculeDTO dto) { return service.create(dto); }

    @GetMapping
    public List<VehiculeDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public VehiculeDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public VehiculeDTO update(@PathVariable Long id, @Valid @RequestBody VehiculeDTO dto) {
        return service.update(id, dto);
    }

    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDTO affecterAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return service.affecterVehiculeAAgence(idVehicule, idAgence);
    }

    @GetMapping("/{id}/agence")
    public AgenceDTO agence(@PathVariable Long id) { return service.findAgence(id); }

    @GetMapping("/{id}/maintenances")
    public List<MaintenanceDTO> maintenances(@PathVariable Long id) {
        return maintenanceService.findByVehicule(id);
    }

    @GetMapping("/contrat/{contratId}")
    public List<VehiculeDTO> parContrat(@PathVariable Long contratId) {
        return service.findByContrat(contratId);
    }

    @GetMapping("/maintenance/{maintenanceId}")
    public List<VehiculeDTO> parMaintenance(@PathVariable Long maintenanceId) {
        return service.findByMaintenance(maintenanceId);
    }

    @PostMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO reserver(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        if (!id.equals(dto.vehiculeId())) {
            throw new IllegalArgumentException("L'identifiant de véhicule du chemin et du corps doit correspondre.");
        }
        return reservationService.create(dto);
    }

    @GetMapping("/{id}/reservations")
    public List<ReservationDTO> reservations(@PathVariable Long id) {
        return reservationService.findByVehicule(id);
    }

    @GetMapping("/{id}/equipements")
    public List<EquipementDTO> equipements(@PathVariable Long id) { return service.findEquipements(id); }

    @PostMapping("/{id}/equipements/{equipementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ajouterEquipement(@PathVariable Long id, @PathVariable Long equipementId) {
        service.ajouterEquipement(id, equipementId);
    }

    @DeleteMapping("/{id}/equipements/{equipementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retirerEquipement(@PathVariable Long id, @PathVariable Long equipementId) {
        service.retirerEquipement(id, equipementId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
