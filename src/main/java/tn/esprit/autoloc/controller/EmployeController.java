package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@Tag(name = "Employé", description = "Gestion des employés")
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    @Operation(summary = "Créer un employé", description = "Crée un nouvel employé et l'associe à son agence et son rôle.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé créé avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Employe create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations de l'employé à créer.", required = true)
                         @RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un employé", description = "Retourne les détails d'un employé selon son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé trouvé."),
            @ApiResponse(responseCode = "404", description = "Employé introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Employe findById(@Parameter(description = "Identifiant de l'employé", required = true, example = "1")
                           @PathVariable Long id) {
        return employeService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les employés", description = "Retourne tous les employés enregistrés dans le système.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des employés récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un employé", description = "Met à jour les informations d'un employé existant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé mis à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Employé introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Employe update(@Parameter(description = "Identifiant de l'employé à mettre à jour", required = true, example = "1")
                         @PathVariable Long id,
                         @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations de l'employé.", required = true)
                         @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un employé", description = "Supprime un employé existant selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé supprimé avec succès."),
            @ApiResponse(responseCode = "404", description = "Employé introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant de l'employé à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
