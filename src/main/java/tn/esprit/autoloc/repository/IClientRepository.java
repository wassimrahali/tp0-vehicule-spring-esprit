package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdClientNot(String email, Long idClient);

    @Query("select r.idReservation from Reservation r where r.client.idClient = :id")
    java.util.List<Long> findReservationIds(@Param("id") Long clientId);
}
