package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
    java.util.Optional<Contrat> findByReservation_IdReservation(Long reservationId);

    @Query("select p.idPaiement from Paiement p where p.contrat.idContrat = :id")
    java.util.List<Long> findPaiementIds(@Param("id") Long contratId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Contrat c where c.idContrat = :id")
    java.util.Optional<Contrat> findForUpdate(@Param("id") Long contratId);
}
