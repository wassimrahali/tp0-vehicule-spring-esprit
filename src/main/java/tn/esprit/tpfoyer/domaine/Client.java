package tn.esprit.tpfoyer.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String telephone;

    @Column(nullable = false, length = 50)
    private String numPermis;

    @Column(nullable = false)
    private LocalDate dateInscription;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations = new ArrayList<>();
}
