package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.service.IReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservation", description = "Gestion des réservations et disponibilités")
public class ReservationController {
    private final IReservationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@Valid @RequestBody ReservationDTO dto) { return service.create(dto); }

    @GetMapping
    public List<ReservationDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ReservationDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public ReservationDTO update(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }

    @GetMapping("/client/{clientId}")
    public List<ReservationDTO> parClient(@PathVariable Long clientId) { return service.findByClient(clientId); }

    @PostMapping("/client/{clientId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO creerPourClient(@PathVariable Long clientId, @Valid @RequestBody ReservationDTO dto) {
        if (!clientId.equals(dto.clientId())) {
            throw new IllegalArgumentException("L'identifiant du client du chemin et du corps doit correspondre.");
        }
        return service.create(dto);
    }

    @GetMapping("/vehicule/{vehiculeId}")
    public List<ReservationDTO> parVehicule(@PathVariable Long vehiculeId) { return service.findByVehicule(vehiculeId); }

    @GetMapping("/vehicule/{vehiculeId}/disponibilite")
    public boolean disponibilite(@PathVariable Long vehiculeId, @RequestParam LocalDate dateDebut,
                                 @RequestParam LocalDate dateFin) {
        return service.estDisponible(vehiculeId, dateDebut, dateFin);
    }

    @PatchMapping("/{id}/statut")
    public ReservationDTO changerStatut(@PathVariable Long id, @RequestParam StatutReservation statut) {
        return service.changerStatut(id, statut);
    }

    @PostMapping("/{id}/annulation")
    public ReservationDTO annuler(@PathVariable Long id) { return service.annuler(id); }
}
