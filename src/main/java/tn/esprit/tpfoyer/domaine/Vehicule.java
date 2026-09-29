package tn.esprit.tpfoyer.domaine;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.tpfoyer.domaine.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategorieVehicule categorie;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutVehicule statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agence_id")
    private Agence agence;

    @OneToMany(mappedBy = "vehicule")
    private List<Maintenance> maintenances = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule")
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "vehicule_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    private Set<Equipement> equipements = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicule vehicule = (Vehicule) o;
        return Objects.equals(immatriculation, vehicule.immatriculation) && Objects.equals(marque, vehicule.marque) && Objects.equals(modele, vehicule.modele) && categorie == vehicule.categorie && Objects.equals(tarifJournalier, vehicule.tarifJournalier) && statut == vehicule.statut;
    }

    @Override
    public int hashCode() {
        return Objects.hash(immatriculation, marque, modele, categorie, tarifJournalier, statut);
    }
}