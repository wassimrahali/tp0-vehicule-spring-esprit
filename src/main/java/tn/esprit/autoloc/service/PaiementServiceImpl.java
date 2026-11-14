package tn.esprit.autoloc.service;

import java.util.List;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.mapper.PaiementMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final IContratRepository contratRepository;
    private final PaiementMapper mapper;

    @Override
    public PaiementDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Paiement findEntity(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<PaiementDTO> findAll() {
        return paiementRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public PaiementDTO create(PaiementDTO dto) {
        Paiement paiement = mapper.toEntity(dto);
        if (paiement.getIdPaiement() != null) {
            throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant.");
        }
        valider(paiement);
        assurerPlafond(paiement.getContrat(), paiement.getMontant(), BigDecimal.ZERO);
        return mapper.toDto(paiementRepository.save(paiement));
    }

    @Override
    @Transactional
    public PaiementDTO createForContrat(Long contratId, PaiementDTO paiement) {
        if (!contratId.equals(paiement.contratId())) {
            throw new IllegalArgumentException("L'identifiant de contrat ne correspond pas.");
        }
        return create(paiement);
    }

    @Override
    @Transactional
    public PaiementDTO update(Long id, PaiementDTO dto) {
        Paiement paiement = mapper.toEntity(dto);
        Paiement existing = findEntity(id);
        valider(paiement);
        BigDecimal deduction = existing.getContrat().getIdContrat().equals(paiement.getContrat().getIdContrat())
                ? existing.getMontant() : BigDecimal.ZERO;
        assurerPlafond(paiement.getContrat(), paiement.getMontant(), deduction);
        existing.setMontant(paiement.getMontant());
        existing.setDatePaiement(paiement.getDatePaiement());
        existing.setModePaiement(paiement.getModePaiement());
        existing.setContrat(paiement.getContrat());
        return mapper.toDto(paiementRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement", id);
        }
        paiementRepository.deleteById(id);
    }

    @Override
    public List<PaiementDTO> findByContrat(Long contratId) {
        if (!contratRepository.existsById(contratId)) {
            throw new ResourceNotFoundException("Contrat", contratId);
        }
        return paiementRepository.findByContrat_IdContrat(contratId).stream().map(mapper::toDto).toList();
    }

    @Override
    public BigDecimal totalForContrat(Long contratId) {
        if (!contratRepository.existsById(contratId)) {
            throw new ResourceNotFoundException("Contrat", contratId);
        }
        return paiementRepository.totalForContrat(contratId);
    }

    @Override
    public boolean contratTotalementPaye(Long contratId) {
        Contrat contrat = contratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", contratId));
        return paiementRepository.totalForContrat(contratId).compareTo(contrat.getMontantTotal()) >= 0;
    }

    private void assurerPlafond(Contrat contrat, BigDecimal montant, BigDecimal deduction) {
        if (contrat == null) {
            throw new IllegalArgumentException("Un paiement doit être lié à un contrat.");
        }
        Contrat managedContrat = contratRepository.findForUpdate(contrat.getIdContrat())
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", contrat.getIdContrat()));
        BigDecimal total = paiementRepository.totalForContrat(managedContrat.getIdContrat())
                .subtract(deduction).add(montant);
        if (total.compareTo(managedContrat.getMontantTotal()) > 0) {
            throw new ResourceConflictException("Le montant cumulé des paiements dépasserait le total du contrat.");
        }
    }

    private void valider(Paiement paiement) {
        if (paiement.getMontant() == null || paiement.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du paiement doit être strictement positif.");
        }
        if (paiement.getDatePaiement() == null || paiement.getModePaiement() == null) {
            throw new IllegalArgumentException("La date et le mode de paiement sont obligatoires.");
        }
    }
}
