package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenance", description = "Gestion des interventions de maintenance")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @PostMapping
    @Operation(summary = "Créer une maintenance", description = "Ajoute un dossier de maintenance pour un véhicule.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Maintenance create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations de la maintenance à créer.", required = true)
                             @RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une maintenance", description = "Retourne le détail d'une intervention de maintenance selon son ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvée."),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Maintenance findById(@Parameter(description = "Identifiant de la maintenance", required = true, example = "1")
                               @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les maintenances", description = "Retourne toutes les interventions de maintenance enregistrées.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des maintenances récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une maintenance", description = "Modifie une intervention de maintenance existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance mise à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Maintenance update(@Parameter(description = "Identifiant de la maintenance à mettre à jour", required = true, example = "1")
                             @PathVariable Long id,
                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations de la maintenance.", required = true)
                             @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une maintenance", description = "Supprime une maintenance existante selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance supprimée avec succès."),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant de la maintenance à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
