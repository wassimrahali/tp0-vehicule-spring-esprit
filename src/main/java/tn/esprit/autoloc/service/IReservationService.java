package tn.esprit.autoloc.service;

import java.util.List;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.dto.ReservationDTO;

public interface IReservationService {

    ReservationDTO create(ReservationDTO entite);

    ReservationDTO findById(Long id);

    List<ReservationDTO> findAll();

    ReservationDTO update(Long id, ReservationDTO entite);

    List<ReservationDTO> findByClient(Long clientId);

    List<ReservationDTO> findByVehicule(Long vehiculeId);

    boolean estDisponible(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin);

    ReservationDTO changerStatut(Long id, StatutReservation statut);

    ReservationDTO annuler(Long id);

    void deleteById(Long id);
}
