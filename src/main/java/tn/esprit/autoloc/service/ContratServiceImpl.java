package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IPaiementRepository paiementRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ContratMapper mapper;

    @Override
    public ContratDTO create(ContratDTO dto) {
        Contrat entite = mapper.toEntity(dto);
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
        return mapper.toDto(contratRepository.save(entite));
    }

    @Override
    public ContratDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Contrat findEntity(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<ContratDTO> findAll() {
        return contratRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ContratDTO update(Long id, ContratDTO dto) {
        Contrat entite = mapper.toEntity(dto);
        Contrat existant = findEntity(id);
        valider(entite);
        existant.setDateSignature(entite.getDateSignature());
        existant.setMontantTotal(entite.getMontantTotal());
        existant.setValide(entite.isValide());
        if (paiementRepository.totalForContrat(id).compareTo(entite.getMontantTotal()) > 0) {
            throw new ResourceConflictException("Le montant du contrat ne peut pas être inférieur aux paiements reçus.");
        }
        return mapper.toDto(contratRepository.save(existant));
    }

    @Override
    public ContratDTO findByReservation(Long reservationId) {
        Contrat contrat = contratRepository.findByReservation_IdReservation(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat associé à la réservation", reservationId));
        return mapper.toDto(contrat);
    }

    @Override
    public List<ContratDTO> findByVehicule(Long vehiculeId) {
        if (!vehiculeRepository.existsById(vehiculeId)) {
            throw new ResourceNotFoundException("Vehicule", vehiculeId);
        }
        return contratRepository.findByReservation_Vehicule_IdVehicule(vehiculeId).stream()
                .map(mapper::toDto).toList();
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
