package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Client", description = "Représente un client locataire du parc automobile.")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique du client.", example = "1")
    private Long idClient;

    @Column(nullable = false, length = 50)
    @Schema(description = "Nom du client.", example = "Ben Ali")
    private String nom;

    @Column(nullable = false, length = 50)
    @Schema(description = "Prénom du client.", example = "Sami")
    private String prenom;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Adresse e-mail du client.", example = "sami@example.com")
    private String email;

    @Column(length = 20)
    @Schema(description = "Numéro de téléphone du client.", example = "+216 20 000 000")
    private String telephone;

    @Column(length = 30)
    @Schema(description = "Numéro du permis de conduire.", example = "12345678")
    private String numPermis;

    @Schema(description = "Date d'inscription du client.", example = "2024-01-15")
    private LocalDate dateInscription;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @Schema(description = "Réservations passées par ce client.")
    private List<Reservation> reservations = new ArrayList<>();
}
