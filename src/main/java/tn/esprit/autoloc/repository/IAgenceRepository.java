package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Agence;

@Repository
public interface IAgenceRepository extends JpaRepository<Agence, Long> {
    @Query("select v.idVehicule from Vehicule v where v.agence.idAgence = :id")
    java.util.List<Long> findVehiculeIds(@Param("id") Long agenceId);

    @Query("select e.idEmploye from Employe e where e.agence.idAgence = :id")
    java.util.List<Long> findEmployeIds(@Param("id") Long agenceId);
}
