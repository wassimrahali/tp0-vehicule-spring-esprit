package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Equipement;
import java.util.List;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
    List<Equipement> findByVehicules_IdVehicule(Long vehiculeId);

    @Query("select v.idVehicule from Vehicule v join v.equipements e where e.idEquipement = :id")
    List<Long> findVehiculeIds(@Param("id") Long equipementId);
}
