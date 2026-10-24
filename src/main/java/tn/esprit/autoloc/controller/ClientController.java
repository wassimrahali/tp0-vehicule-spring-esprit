package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client", description = "Gestion des clients")
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    @Operation(summary = "Créer un client", description = "Crée un nouveau client avec ses informations personnelles et sa licence.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Données client invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Client create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations du client à créer.", required = true)
                        @RequestBody Client client) {
        return clientService.create(client);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un client par identifiant", description = "Retourne les détails d'un client selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé."),
            @ApiResponse(responseCode = "404", description = "Client introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Client findById(@Parameter(description = "Identifiant du client", required = true, example = "1")
                          @PathVariable Long id) {
        return clientService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les clients", description = "Retourne la liste de tous les clients du système.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des clients récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un client", description = "Modifie les informations d'un client existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client mis à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Client introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Client update(@Parameter(description = "Identifiant du client à mettre à jour", required = true, example = "1")
                        @PathVariable Long id,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations du client.", required = true)
                        @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client", description = "Supprime un client existant selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Client introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant du client à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        clientService.deleteById(id);
    }
}
