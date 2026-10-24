package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Paiement", description = "Paiement associé à un contrat de location.")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique du paiement.", example = "1")
    private Long idPaiement;

    @Column(nullable = false, precision = 10, scale = 2)
    @Schema(description = "Montant du paiement.", example = "150.00")
    private BigDecimal montant;

    @Schema(description = "Date du paiement.", example = "2024-03-02")
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Schema(description = "Mode de paiement utilisé.", allowableValues = {"CARTE", "ESPECES", "VIREMENT"})
    private ModePaiement modePaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @Schema(description = "Contrat associé au paiement.")
    private Contrat contrat;
}
