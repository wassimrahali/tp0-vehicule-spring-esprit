package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;

@Component
@RequiredArgsConstructor
public class PaiementMapper {
    private final IContratRepository contratRepository;

    public PaiementDTO toDto(Paiement entity) {
        return new PaiementDTO(entity.getIdPaiement(), entity.getMontant(), entity.getDatePaiement(),
                entity.getModePaiement(), entity.getContrat() == null ? null : entity.getContrat().getIdContrat());
    }

    public Paiement toEntity(PaiementDTO dto) {
        Paiement entity = new Paiement();
        entity.setIdPaiement(dto.idPaiement());
        entity.setMontant(dto.montant());
        entity.setDatePaiement(dto.datePaiement());
        entity.setModePaiement(dto.modePaiement());
        entity.setContrat(contratRepository.findById(dto.contratId())
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", dto.contratId())));
        return entity;
    }
}
