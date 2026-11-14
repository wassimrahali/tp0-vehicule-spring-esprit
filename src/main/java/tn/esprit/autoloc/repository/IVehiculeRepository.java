package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Vehicule;
import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByAgence_IdAgence(Long agenceId);
    boolean existsByImmatriculation(String immatriculation);
    boolean existsByImmatriculationAndIdVehiculeNot(String immatriculation, Long idVehicule);
    List<Vehicule> findByEquipements_IdEquipement(Long equipementId);

    @Query("select e.idEquipement from Vehicule v join v.equipements e where v.idVehicule = :id")
    List<Long> findEquipementIds(@Param("id") Long vehiculeId);

    @Query("select m.idMaintenance from Maintenance m where m.vehicule.idVehicule = :id")
    List<Long> findMaintenanceIds(@Param("id") Long vehiculeId);

    @Query("select r.idReservation from Reservation r where r.vehicule.idVehicule = :id")
    List<Long> findReservationIds(@Param("id") Long vehiculeId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select v from Vehicule v where v.idVehicule = :id")
    java.util.Optional<Vehicule> findForUpdate(@Param("id") Long vehiculeId);
}
