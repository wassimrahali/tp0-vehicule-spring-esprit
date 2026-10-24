package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@Tag(name = "Agence", description = "Gestion des agences de location")
public class AgenceController {

    private final IAgenceService agenceService;

    @PostMapping
    @Operation(summary = "Créer une agence", description = "Crée une nouvelle agence avec ses informations de base.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides ou agence incomplète."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Agence create(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objet agence à créer, sans identifiant.", required = true) @RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une agence par identifiant", description = "Retourne les détails complets d'une agence selon son ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée."),
            @ApiResponse(responseCode = "404", description = "Agence introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Agence findById(@Parameter(description = "Identifiant unique de l'agence", required = true, example = "1")
                           @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les agences", description = "Retourne la liste complète de toutes les agences enregistrées.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des agences récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une agence", description = "Modifie les informations d'une agence existante selon son ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence mise à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Agence introuvable."),
            @ApiResponse(responseCode = "400", description = "Données de mise à jour invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Agence update(@Parameter(description = "Identifiant de l'agence à mettre à jour", required = true, example = "1")
                        @PathVariable Long id,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations de l'agence.", required = true)
                        @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une agence", description = "Supprime une agence existante selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence supprimée avec succès."),
            @ApiResponse(responseCode = "404", description = "Agence introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant de l'agence à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}