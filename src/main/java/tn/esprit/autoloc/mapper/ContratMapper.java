package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IContratRepository;

@Component
@RequiredArgsConstructor
public class ContratMapper {
    private final IReservationRepository reservationRepository;
    private final IContratRepository contratRepository;

    public ContratDTO toDto(Contrat entity) {
        return new ContratDTO(entity.getIdContrat(), entity.getDateSignature(), entity.getMontantTotal(),
                entity.isValide(), entity.getReservation() == null ? null : entity.getReservation().getIdReservation(),
                contratRepository.findPaiementIds(entity.getIdContrat()));
    }

    public Contrat toEntity(ContratDTO dto) {
        Contrat entity = new Contrat();
        entity.setIdContrat(dto.idContrat());
        entity.setDateSignature(dto.dateSignature());
        entity.setMontantTotal(dto.montantTotal());
        entity.setValide(dto.valide());
        entity.setReservation(reservationRepository.findById(dto.reservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", dto.reservationId())));
        return entity;
    }
}
