package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
@Tag(name = "Equipement", description = "Gestion des équipements du parc automobile")
public class EquipementController {

    private final IEquipementService equipementService;

    @PostMapping
    @Operation(summary = "Créer un équipement", description = "Ajoute un nouvel équipement disponible pour les véhicules.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Equipement create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations de l'équipement à créer.", required = true)
                            @RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un équipement", description = "Retourne les détails d'un équipement selon son ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement trouvé."),
            @ApiResponse(responseCode = "404", description = "Équipement introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Equipement findById(@Parameter(description = "Identifiant de l'équipement", required = true, example = "1")
                              @PathVariable Long id) {
        return equipementService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les équipements", description = "Retourne la liste complète des équipements disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des équipements récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un équipement", description = "Modifie les informations d'un équipement existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement mis à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Équipement introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Equipement update(@Parameter(description = "Identifiant de l'équipement à mettre à jour", required = true, example = "1")
                            @PathVariable Long id,
                            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations de l'équipement.", required = true)
                            @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un équipement", description = "Supprime un équipement existant selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Équipement supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Équipement introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant de l'équipement à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
