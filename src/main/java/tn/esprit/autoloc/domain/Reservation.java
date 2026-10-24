package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Reservation", description = "Réservation d'un véhicule pour un client donné.")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de la réservation.", example = "1")
    private Long idReservation;

    @Column(nullable = false)
    @Schema(description = "Date de début de la réservation.", example = "2024-03-01")
    private LocalDate dateDebut;

    @Column(nullable = false)
    @Schema(description = "Date de fin de la réservation.", example = "2024-03-05")
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Statut actuel de la réservation.", allowableValues = {"EN_ATTENTE", "CONFIRMEE", "ANNULEE", "TERMINEE"})
    private StatutReservation statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Client ayant effectué la réservation.")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Véhicule réservé.")
    private Vehicule vehicule;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Schema(description = "Contrat associé à cette réservation, s'il existe.")
    private Contrat contrat;
}
