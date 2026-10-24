package tn.esprit.autoloc.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservation", description = "Gestion des réservations de véhicules")
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    @Operation(summary = "Créer une réservation", description = "Crée une nouvelle réservation pour un client et un véhicule.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation créée avec succès."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Reservation create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Informations de la réservation à créer.", required = true)
                             @RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une réservation", description = "Retourne le détail d'une réservation selon son identifiant.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation trouvée."),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Reservation findById(@Parameter(description = "Identifiant de la réservation", required = true, example = "1")
                               @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Lister les réservations", description = "Retourne toutes les réservations enregistrées.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des réservations récupérée avec succès."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une réservation", description = "Modifie les informations d'une réservation existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation mise à jour avec succès."),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable."),
            @ApiResponse(responseCode = "400", description = "Données invalides."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public Reservation update(@Parameter(description = "Identifiant de la réservation à mettre à jour", required = true, example = "1")
                             @PathVariable Long id,
                             @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles informations de la réservation.", required = true)
                             @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une réservation", description = "Supprime une réservation selon son identifiant unique.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réservation supprimée avec succès."),
            @ApiResponse(responseCode = "404", description = "Réservation introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur.")
    })
    public void deleteById(@Parameter(description = "Identifiant de la réservation à supprimer", required = true, example = "1")
                          @PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
