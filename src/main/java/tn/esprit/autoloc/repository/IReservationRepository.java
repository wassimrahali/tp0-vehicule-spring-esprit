package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClient_IdClient(Long clientId);
    List<Reservation> findByVehicule_IdVehicule(Long vehiculeId);
    boolean existsByVehicule_IdVehiculeAndStatutInAndDateDebutLessThanEqualAndDateFinGreaterThanEqual(
            Long vehiculeId, Collection<StatutReservation> statuts, LocalDate dateFin, LocalDate dateDebut);
    boolean existsByContrat_IdContrat(Long contratId);

    @Query("""
            select case when count(r) > 0 then true else false end
            from Reservation r
            where r.vehicule.idVehicule = :vehiculeId
              and r.statut in :statuts
              and r.dateDebut <= :dateFin
              and r.dateFin >= :dateDebut
              and (:reservationId is null or r.idReservation <> :reservationId)
            """)
    boolean existsOverlappingReservation(@Param("vehiculeId") Long vehiculeId,
                                         @Param("dateDebut") LocalDate dateDebut,
                                         @Param("dateFin") LocalDate dateFin,
                                         @Param("statuts") Collection<StatutReservation> statuts,
                                         @Param("reservationId") Long reservationId);
}
