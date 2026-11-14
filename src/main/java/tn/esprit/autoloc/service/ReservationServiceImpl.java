package tn.esprit.autoloc.service;

import java.util.List;
import java.time.LocalDate;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IClientRepository clientRepository;
    private final ReservationMapper mapper;

    @Override
    @Transactional
    public ReservationDTO create(ReservationDTO dto) {
        Reservation entite = mapper.toEntity(dto);
        if (entite.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau reservation ne doit pas avoir d'identifiant");
        }
        valider(entite);
        if (entite.getStatut() != StatutReservation.EN_ATTENTE
                && entite.getStatut() != StatutReservation.CONFIRMEE) {
            throw new IllegalArgumentException("Une réservation doit être créée en attente ou confirmée.");
        }
        assurerDisponibilite(entite, null);
        return mapper.toDto(reservationRepository.save(entite));
    }

    @Override
    public ReservationDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Reservation findEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public ReservationDTO update(Long id, ReservationDTO dto) {
        Reservation entite = mapper.toEntity(dto);
        Reservation existant = findEntity(id);
        valider(entite);
        if (existant.getStatut() != entite.getStatut() && !transitionAutorisee(existant.getStatut(), entite.getStatut())) {
            throw new ResourceConflictException("Transition de statut de réservation non autorisée.");
        }
        if (entite.getStatut() == StatutReservation.EN_ATTENTE
                || entite.getStatut() == StatutReservation.CONFIRMEE) {
            assurerDisponibilite(entite, id);
        }
        existant.setDateDebut(entite.getDateDebut());
        existant.setDateFin(entite.getDateFin());
        existant.setStatut(entite.getStatut());
        existant.setClient(entite.getClient());
        existant.setVehicule(entite.getVehicule());
        return mapper.toDto(reservationRepository.save(existant));
    }

    @Override
    public List<ReservationDTO> findByClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client", clientId);
        }
        return reservationRepository.findByClient_IdClient(clientId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<ReservationDTO> findByVehicule(Long vehiculeId) {
        if (!vehiculeRepository.existsById(vehiculeId)) {
            throw new ResourceNotFoundException("Vehicule", vehiculeId);
        }
        return reservationRepository.findByVehicule_IdVehicule(vehiculeId).stream().map(mapper::toDto).toList();
    }

    @Override
    public boolean estDisponible(Long vehiculeId, LocalDate dateDebut, LocalDate dateFin) {
        if (dateDebut == null || dateFin == null || !dateFin.isAfter(dateDebut)) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début.");
        }
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehiculeId));
        return vehicule.getStatut() != StatutVehicule.MAINTENANCE
                && !reservationRepository.existsOverlappingReservation(vehiculeId, dateDebut, dateFin,
                EnumSet.of(StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE), null);
    }

    @Override
    @Transactional
    public ReservationDTO changerStatut(Long id, StatutReservation statut) {
        Reservation reservation = findEntity(id);
        if (statut == null) {
            throw new IllegalArgumentException("Le statut est obligatoire.");
        }
        if (statut == reservation.getStatut()) {
            return mapper.toDto(reservation);
        }
        if (!transitionAutorisee(reservation.getStatut(), statut)) {
            throw new ResourceConflictException("Transition de statut de réservation non autorisée.");
        }
        if (statut == StatutReservation.CONFIRMEE) {
            assurerDisponibilite(reservation, id);
        }
        reservation.setStatut(statut);
        return mapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDTO annuler(Long id) {
        return changerStatut(id, StatutReservation.ANNULEE);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private void valider(Reservation e) {
        if (e.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début est obligatoire");
        }
        if (e.getDateFin() == null) {
            throw new IllegalArgumentException("La date de fin est obligatoire");
        }
        if (!e.getDateFin().isAfter(e.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début.");
        }
        if (e.getClient() == null || e.getVehicule() == null || e.getStatut() == null) {
            throw new IllegalArgumentException("Le client, le véhicule et le statut sont obligatoires.");
        }
    }

    private void assurerDisponibilite(Reservation reservation, Long reservationId) {
        Long vehiculeId = reservation.getVehicule().getIdVehicule();
        Vehicule vehicule = vehiculeRepository.findForUpdate(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", vehiculeId));
        if (vehicule.getStatut() == StatutVehicule.MAINTENANCE) {
            throw new ResourceConflictException("Le véhicule n'est pas disponible à la location.");
        }
        if (reservationRepository.existsOverlappingReservation(vehiculeId,
                reservation.getDateDebut(), reservation.getDateFin(),
                EnumSet.of(StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE), reservationId)) {
            throw new ResourceConflictException("Une réservation existe déjà sur cette période.");
        }
    }

    private boolean transitionAutorisee(StatutReservation actuel, StatutReservation cible) {
        return (actuel == StatutReservation.EN_ATTENTE
                && (cible == StatutReservation.CONFIRMEE || cible == StatutReservation.ANNULEE))
                || (actuel == StatutReservation.CONFIRMEE
                && (cible == StatutReservation.ANNULEE || cible == StatutReservation.TERMINEE));
    }
}
