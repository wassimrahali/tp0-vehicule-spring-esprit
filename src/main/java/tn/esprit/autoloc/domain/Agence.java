package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Agence", description = "Représente une agence de location automobile.")
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de l'agence.", example = "1")
    private Long idAgence;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nom commercial de l'agence.", example = "AutoLoc Center")
    private String nom;

    @Column(length = 100)
    @Schema(description = "Ville où se trouve l'agence.", example = "Tunis")
    private String ville;

    @Column(length = 200)
    @Schema(description = "Adresse complète de l'agence.", example = "Avenue Habib Bourguiba 12")
    private String adresse;

    @Column(length = 20)
    @Schema(description = "Numéro de téléphone de l'agence.", example = "+216 71 000 000")
    private String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @Schema(description = "Véhicules rattachés à cette agence.")
    private List<Vehicule> vehicules = new ArrayList<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @Schema(description = "Employés rattachés à cette agence.")
    private List<Employe> employes = new ArrayList<>();
}
