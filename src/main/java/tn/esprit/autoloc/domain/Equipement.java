package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Equipement", description = "Équipement optionnel disponible sur certains véhicules.")
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de l'équipement.", example = "1")
    private Long idEquipement;

    @Column(nullable = false, length = 100)
    @Schema(description = "Libellé de l'équipement.", example = "Bluetooth")
    private String libelle;

    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    @Schema(description = "Véhicules associés à cet équipement.")
    private Set<Vehicule> vehicules = new HashSet<>();
}
