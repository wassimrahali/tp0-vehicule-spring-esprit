package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contrat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Contrat", description = "Contrat de location associé à une réservation.")
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique du contrat.", example = "1")
    private Long idContrat;

    @Schema(description = "Date de signature du contrat.", example = "2024-02-10")
    private LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Montant total du contrat.", example = "450.00")
    private BigDecimal montantTotal;

    @Schema(description = "Indique si le contrat est validé.", example = "true")
    private boolean valide;

    @OneToOne(fetch = FetchType.LAZY)
    @Schema(description = "Réservation associée au contrat.")
    private Reservation reservation;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @Schema(description = "Paiements associés à ce contrat.")
    private List<Paiement> paiements = new ArrayList<>();

    public void addPaiement(Paiement paiement) {
        paiements.add(paiement);
        paiement.setContrat(this);
    }

    public void removePaiement(Paiement paiement) {
        paiements.remove(paiement);
        paiement.setContrat(null);
    }
}
