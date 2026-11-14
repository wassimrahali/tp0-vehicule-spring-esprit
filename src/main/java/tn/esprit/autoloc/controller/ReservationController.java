package tn.esprit.autoloc.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservation", description = "Gestion des réservations, de leur statut et de la disponibilité des véhicules")
public class ReservationController {
    private final IReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@Valid @RequestBody ReservationDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping
    public List<ReservationDTO> findAll() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ReservationDTO findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @PutMapping("/{id}")
    public ReservationDTO update(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/client/{clientId}")
    public List<ReservationDTO> parClient(@PathVariable Long clientId) {
        return service.findByClient(clientId).stream().map(mapper::toDto).toList();
    }

    @PostMapping("/client/{clientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO creerPourClient(@PathVariable Long clientId, @Valid @RequestBody ReservationDTO dto) {
        if (!clientId.equals(dto.clientId())) {
            throw new IllegalArgumentException("L'identifiant du client du chemin et du corps doit correspondre.");
        }
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping("/vehicule/{vehiculeId}")
    public List<ReservationDTO> parVehicule(@PathVariable Long vehiculeId) {
        return service.findByVehicule(vehiculeId).stream().map(mapper::toDto).toList();
    }

    @GetMapping("/vehicule/{vehiculeId}/disponibilite")
    public boolean disponibilite(@PathVariable Long vehiculeId,
                                 @RequestParam LocalDate dateDebut,
                                 @RequestParam LocalDate dateFin) {
        return service.estDisponible(vehiculeId, dateDebut, dateFin);
    }

    @PatchMapping("/{id}/statut")
    public ReservationDTO changerStatut(@PathVariable Long id, @RequestParam StatutReservation statut) {
        return mapper.toDto(service.changerStatut(id, statut));
    }

    @PostMapping("/{id}/annulation")
    public ReservationDTO annuler(@PathVariable Long id) {
        return mapper.toDto(service.annuler(id));
    }
}
