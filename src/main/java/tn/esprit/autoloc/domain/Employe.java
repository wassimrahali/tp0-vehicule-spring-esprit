package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Employe", description = "Employé travaillant au sein d'une agence.")
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de l'employé.", example = "1")
    private Long idEmploye;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nom de l'employé.", example = "Khaled")
    private String nom;

    @Column(nullable = false, length = 50)
    @Schema(description = "Prénom de l'employé.", example = "Mourad")
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Rôle de l'employé.", allowableValues = {"AGENT", "MANAGER"})
    private RoleEmploye role;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Agence à laquelle l'employé est rattaché.")
    private Agence agence;
}
