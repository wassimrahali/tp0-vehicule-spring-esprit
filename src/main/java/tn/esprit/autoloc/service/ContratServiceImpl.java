package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IPaiementRepository paiementRepository;

    @Override
    public Contrat create(Contrat entite) {
        if (entite.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        valider(entite);
        if (entite.getReservation() == null) {
            throw new IllegalArgumentException("Un contrat doit être lié à une réservation.");
        }
        if (contratRepository.findByReservation_IdReservation(
                entite.getReservation().getIdReservation()).isPresent()) {
            throw new ResourceConflictException("Cette réservation possède déjà un contrat.");
        }
        entite.getReservation().setContrat(entite);
        return contratRepository.save(entite);
    }

    @Override
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat entite) {
        Contrat existant = findById(id);
        valider(entite);
        existant.setDateSignature(entite.getDateSignature());
        existant.setMontantTotal(entite.getMontantTotal());
        existant.setValide(entite.isValide());
        if (paiementRepository.totalForContrat(id).compareTo(entite.getMontantTotal()) > 0) {
            throw new ResourceConflictException("Le montant du contrat ne peut pas être inférieur aux paiements reçus.");
        }
        return contratRepository.save(existant);
    }

    @Override
    public Contrat findByReservation(Long reservationId) {
        return contratRepository.findByReservation_IdReservation(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat associé à la réservation", reservationId));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private void valider(Contrat e) {
        if (e.getMontantTotal() == null) {
            throw new IllegalArgumentException("Le montant total est obligatoire");
        }
        if (e.getMontantTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant total doit être strictement positif");
        }
    }
}
