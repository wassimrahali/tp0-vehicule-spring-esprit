package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Maintenance", description = "Intervention de maintenance d'un véhicule.")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de la maintenance.", example = "1")
    private Long idMaintenance;

    @Column(nullable = false)
    @Schema(description = "Date de début de la maintenance.", example = "2024-04-10")
    private LocalDate dateDebut;

    @Schema(description = "Date de fin prévue ou réelle de la maintenance.", example = "2024-04-15")
    private LocalDate dateFin;

    @Column(length = 255)
    @Schema(description = "Description détaillée de la maintenance réalisée.", example = "Changement de pneus et contrôle du freinage")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Véhicule concerné par la maintenance.")
    private Vehicule vehicule;
}
