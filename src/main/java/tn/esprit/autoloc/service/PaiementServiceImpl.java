package tn.esprit.autoloc.service;

import java.util.List;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final IContratRepository contratRepository;

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    @Override
    @Transactional
    public Paiement create(Paiement paiement) {
        if (paiement.getIdPaiement() != null) {
            throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant.");
        }
        valider(paiement);
        assurerPlafond(paiement.getContrat(), paiement.getMontant(), BigDecimal.ZERO);
        return paiementRepository.save(paiement);
    }

    @Override
    @Transactional
    public Paiement createForContrat(Long contratId, Paiement paiement) {
        paiement.setContrat(contratRepository.findById(contratId)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", contratId)));
        paiement.setIdPaiement(null);
        return create(paiement);
    }

    @Override
    @Transactional
    public Paiement update(Long id, Paiement paiement) {
        Paiement existing = findById(id);
        valider(paiement);
        BigDecimal deduction = existing.getContrat().getIdContrat().equals(paiement.getContrat().getIdContrat())
                ? existing.getMontant() : BigDecimal.ZERO;
        assurerPlafond(paiement.getContrat(), paiement.getMontant(), deduction);
        existing.setMontant(paiement.getMontant());
        existing.setDatePaiement(paiement.getDatePaiement());
        existing.setModePaiement(paiement.getModePaiement());
        existing.setContrat(paiement.getContrat());
        return paiementRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement", id);
        }
        paiementRepository.deleteById(id);
    }

    @Override
    public List<Paiement> findByContrat(Long contratId) {
        if (!contratRepository.existsById(contratId)) {
            throw new ResourceNotFoundException("Contrat", contratId);
        }
        return paiementRepository.findByContrat_IdContrat(contratId);
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
