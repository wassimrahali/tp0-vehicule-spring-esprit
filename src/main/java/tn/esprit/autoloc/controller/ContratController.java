package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrat", description = "Gestion des contrats de location")
public class ContratController {

    private final IContratService contratService;

    @PostMapping
    @Operation(summary = "Créer un contrat", description = "Crée un nouveau contrat de location lié à une réservation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides ou contrat incomplet."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Contrat create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations du contrat à créer.", required = true)
                         @RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un contrat par identifiant", description = "Retourne les informations détaillées d'un contrat selon son ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat trouvé."),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Contrat findById(@Parameter(description = "Identifiant du contrat", required = true, example = "1")
                           @PathVariable Long id) {
        return contratService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les contrats", description = "Retourne la liste de tous les contrats enregistrés.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des contrats récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un contrat", description = "Modifie un contrat déjà existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat mis à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Contrat update(@Parameter(description = "Identifiant du contrat à mettre à jour", required = true, example = "1")
                         @PathVariable Long id,
                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations du contrat.", required = true)
                         @RequestBody Contrat contrat) {
        return contratService.update(id, contrat);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un contrat", description = "Supprime un contrat existant selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant du contrat à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        contratService.deleteById(id);
    }
}
