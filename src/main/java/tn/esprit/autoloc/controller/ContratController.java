package tn.esprit.autoloc.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.service.IPaiementService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrat", description = "Gestion des contrats et paiements")
public class ContratController {
    private final IContratService service;
    private final IPaiementService paiementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO create(@Valid @RequestBody ContratDTO dto) { return service.create(dto); }

    @GetMapping
    public List<ContratDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ContratDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public ContratDTO update(@PathVariable Long id, @Valid @RequestBody ContratDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }

    @PostMapping("/reservation/{reservationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO genererPourReservation(@PathVariable Long reservationId, @Valid @RequestBody ContratDTO dto) {
        if (!reservationId.equals(dto.reservationId())) {
            throw new IllegalArgumentException("L'identifiant de réservation du chemin et du corps doit correspondre.");
        }
        return service.create(dto);
    }

    @GetMapping("/reservation/{reservationId}")
    public ContratDTO contratDeReservation(@PathVariable Long reservationId) {
        return service.findByReservation(reservationId);
    }

    @GetMapping("/vehicule/{vehiculeId}")
    public List<ContratDTO> contratsDuVehicule(@PathVariable Long vehiculeId) {
        return service.findByVehicule(vehiculeId);
    }

    @PostMapping("/{contratId}/paiements")
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDTO ajouterPaiement(@PathVariable Long contratId, @Valid @RequestBody PaiementDTO dto) {
        return paiementService.createForContrat(contratId, dto);
    }

    @GetMapping("/{contratId}/paiements")
    public List<PaiementDTO> paiements(@PathVariable Long contratId) { return paiementService.findByContrat(contratId); }

    @GetMapping("/{contratId}/paiements/total")
    public BigDecimal montantPaye(@PathVariable Long contratId) { return paiementService.totalForContrat(contratId); }

    @GetMapping("/{contratId}/paye")
    public boolean estTotalementPaye(@PathVariable Long contratId) {
        return paiementService.contratTotalementPaye(contratId);
    }
}
