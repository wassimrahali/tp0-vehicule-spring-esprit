package tn.esprit.autoloc.controller;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrat", description = "Gestion des contrats de réservation et de leurs paiements")
public class ContratController {
    private final IContratService service;
    private final ContratMapper mapper;
    private final IPaiementService paiementService;
    private final PaiementMapper paiementMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO create(@Valid @RequestBody ContratDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping
    public List<ContratDTO> findAll() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ContratDTO findById(@PathVariable Long id) {
        return mapper.toDto(service.findById(id));
    }

    @PutMapping("/{id}")
    public ContratDTO update(@PathVariable Long id, @Valid @RequestBody ContratDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PostMapping("/reservation/{reservationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO genererPourReservation(@PathVariable Long reservationId,
                                             @Valid @RequestBody ContratDTO dto) {
        if (!reservationId.equals(dto.reservationId())) {
            throw new IllegalArgumentException("L'identifiant de réservation du chemin et du corps doit correspondre.");
        }
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @GetMapping("/reservation/{reservationId}")
    public ContratDTO contratDeReservation(@PathVariable Long reservationId) {
        return mapper.toDto(service.findByReservation(reservationId));
    }

    @PostMapping("/{contratId}/paiements")
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDTO ajouterPaiement(@PathVariable Long contratId, @Valid @RequestBody PaiementDTO dto) {
        if (!contratId.equals(dto.contratId())) {
            throw new IllegalArgumentException("L'identifiant de contrat du chemin et du corps doit correspondre.");
        }
        return paiementMapper.toDto(paiementService.createForContrat(
                contratId, paiementMapper.toEntity(dto)));
    }

    @GetMapping("/{contratId}/paiements")
    public List<PaiementDTO> paiements(@PathVariable Long contratId) {
        return paiementService.findByContrat(contratId).stream().map(paiementMapper::toDto).toList();
    }

    @GetMapping("/{contratId}/paiements/total")
    public BigDecimal montantPaye(@PathVariable Long contratId) {
        return paiementService.totalForContrat(contratId);
    }

    @GetMapping("/{contratId}/paye")
    public boolean estTotalementPaye(@PathVariable Long contratId) {
        return paiementService.contratTotalementPaye(contratId);
    }
}
