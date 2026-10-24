package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Véhicule", description = "Gestion du parc automobile")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @PostMapping
    @Operation(summary = "Créer un véhicule", description = "Ajoute un nouveau véhicule au parc automobile avec ses caractéristiques et son statut.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Vehicule create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations du véhicule à créer.", required = true)
                          @RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un véhicule", description = "Retourne les informations détaillées d'un véhicule selon son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé."),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Vehicule findById(@Parameter(description = "Identifiant du véhicule", required = true, example = "1")
                            @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les véhicules", description = "Retourne la liste complète des véhicules disponibles dans le parc.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des véhicules récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un véhicule", description = "Modifie les informations d'un véhicule existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule mis à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Vehicule update(@Parameter(description = "Identifiant du véhicule à mettre à jour", required = true, example = "1")
                          @PathVariable Long id,
                          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations du véhicule.", required = true)
                          @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule", description = "Supprime un véhicule existant selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Véhicule supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Véhicule introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant du véhicule à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
