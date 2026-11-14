package tn.esprit.autoloc.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.dto.MaintenanceDTO;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicule", description = "Gestion des véhicules et de leurs équipements, maintenances et réservations")
public class VehiculeController {
    private final IVehiculeService service;
    private final VehiculeMapper mapper;
    private final IEquipementService equipementService;
    private final EquipementMapper equipementMapper;
    private final IMaintenanceService maintenanceService;
    private final MaintenanceMapper maintenanceMapper;
    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final IAgenceService agenceService;
    private final AgenceMapper agenceMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO create(@Valid @RequestBody VehiculeDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping
    public List<VehiculeDTO> findAll() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public VehiculeDTO findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @PutMapping("/{id}")
    public VehiculeDTO update(@PathVariable Long id, @Valid @RequestBody VehiculeDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDTO affecterAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return mapper.toDto(service.affecterVehiculeAAgence(idVehicule, idAgence));
    }

    @GetMapping("/{id}/agence")
    public AgenceDTO agence(@PathVariable Long id) {
        Long agenceId = service.findById(id).getAgence().getIdAgence();
        return agenceMapper.toDto(agenceService.findById(agenceId));
    }

    @GetMapping("/{id}/maintenances")
    public List<MaintenanceDTO> maintenances(@PathVariable Long id) {
        return maintenanceService.findByVehicule(id).stream().map(maintenanceMapper::toDto).toList();
    }

    @GetMapping("/{id}/reservations")
    public List<ReservationDTO> reservations(@PathVariable Long id) {
        return reservationService.findByVehicule(id).stream().map(reservationMapper::toDto).toList();
    }

    @PostMapping("/{id}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO reserver(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        if (!id.equals(dto.vehiculeId())) {
            throw new IllegalArgumentException("L'identifiant de véhicule du chemin et du corps doit correspondre.");
        }
        return reservationMapper.toDto(reservationService.create(reservationMapper.toEntity(dto)));
    }

    @GetMapping("/{id}/equipements")
    public List<EquipementDTO> equipements(@PathVariable Long id) {
        return service.findEquipements(id).stream().map(equipementMapper::toDto).toList();
    }

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
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
