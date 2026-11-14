package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Paiement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByContrat_IdContrat(Long contratId);

    @Query("select coalesce(sum(p.montant), 0) from Paiement p where p.contrat.idContrat = :contratId")
    BigDecimal totalForContrat(@Param("contratId") Long contratId);
}
