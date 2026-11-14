package tn.esprit.autoloc.service;

import java.util.List;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Reservation;

public interface IReservationService {

    Reservation create(Reservation entite);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation entite);

    List<Reservation> findByClient(Long clientId);

    List<Reservation> findByVehicule(Long vehiculeId);

    boolean estDisponible(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin);

    Reservation changerStatut(Long id, StatutReservation statut);

    Reservation annuler(Long id);

    void deleteById(Long id);
}
