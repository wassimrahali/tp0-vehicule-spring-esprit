package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;

    public ReservationDTO toDto(Reservation entity) {
        Long contratId = contratRepository.findByReservation_IdReservation(entity.getIdReservation())
                .map(contrat -> contrat.getIdContrat()).orElse(null);
        return new ReservationDTO(entity.getIdReservation(), entity.getDateDebut(), entity.getDateFin(),
                entity.getStatut(),
                entity.getClient() == null ? null : entity.getClient().getIdClient(),
                entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule(),
                contratId);
    }

    public Reservation toEntity(ReservationDTO dto) {
        Reservation entity = new Reservation();
        entity.setIdReservation(dto.idReservation());
        entity.setDateDebut(dto.dateDebut());
        entity.setDateFin(dto.dateFin());
        entity.setStatut(dto.statut());
        entity.setClient(clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", dto.clientId())));
        entity.setVehicule(vehiculeRepository.findById(dto.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.vehiculeId())));
        if (dto.contratId() != null) {
            entity.setContrat(contratRepository.findById(dto.contratId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contrat", dto.contratId())));
        }
        return entity;
    }
}
