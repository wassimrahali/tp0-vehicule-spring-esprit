package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Vehicule", description = "Véhicule disponible dans le parc automobile.")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique du véhicule.", example = "1")
    private Long idVehicule;

    @Column(nullable = false, unique = true, length = 20)
    @Schema(description = "Immatriculation du véhicule.", example = "123-TN-4567")
    private String immatriculation;

    @Column(nullable = false, length = 50)
    @Schema(description = "Marque du véhicule.", example = "Toyota")
    private String marque;

    @Column(nullable = false, length = 50)
    @Schema(description = "Modèle du véhicule.", example = "Corolla")
    private String modele;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Catégorie du véhicule.", allowableValues = {"CITADINE", "BERLINE", "SUV", "UTILITAIRE"})
    private CategorieVehicule categorie;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Tarif journalier de location.", example = "65.00")
    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Statut actuel du véhicule.", allowableValues = {"DISPONIBLE", "LOUE", "MAINTENANCE"})
    private StatutVehicule statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Agence affectée au véhicule.")
    private Agence agence;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "id_equipement"))
    @Schema(description = "Équipements installés ou inclus dans le véhicule.")
    private Set<Equipement> equipements = new HashSet<>();

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Schema(description = "Historique des maintenances du véhicule.")
    private List<Maintenance> maintenances = new ArrayList<>();
}
