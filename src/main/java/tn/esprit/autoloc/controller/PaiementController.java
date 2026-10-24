package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiement", description = "Gestion des paiements")
public class PaiementController {

    private final IPaiementService paiementService;

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un paiement", description = "Retourne le détail d'un paiement selon son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement trouvé."),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Paiement findById(@Parameter(description = "Identifiant du paiement", required = true, example = "1")
                            @PathVariable Long id) {
        return paiementService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les paiements", description = "Retourne tous les paiements associés aux contrats.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des paiements récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }
}
