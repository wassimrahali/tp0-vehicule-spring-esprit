package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    public Vehicule create(Vehicule entite) {
        if (entite.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau vehicule ne doit pas avoir d'identifiant");
        }
        valider(entite);
        if (vehiculeRepository.existsByImmatriculation(entite.getImmatriculation())) {
            throw new ResourceConflictException("Cette immatriculation est déjà utilisée.");
        }
        if (entite.getAgence() == null) {
            throw new IllegalArgumentException("Un véhicule doit appartenir à une agence.");
        }
        return vehiculeRepository.save(entite);
    }

    @Override
    public Vehicule findById(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entite) {
        Vehicule existant = findById(id);
        valider(entite);
        if (vehiculeRepository.existsByImmatriculationAndIdVehiculeNot(entite.getImmatriculation(), id)) {
            throw new ResourceConflictException("Cette immatriculation est déjà utilisée.");
        }
        existant.setImmatriculation(entite.getImmatriculation());
        existant.setMarque(entite.getMarque());
        existant.setModele(entite.getModele());
        existant.setCategorie(entite.getCategorie());
        existant.setTarifJournalier(entite.getTarifJournalier());
        existant.setStatut(entite.getStatut());
        existant.setAgence(entite.getAgence());
        if (entite.getEquipements() != null) {
            existant.setEquipements(new java.util.HashSet<>(entite.getEquipements()));
        }
        return vehiculeRepository.save(existant);
    }

    @Override
    @Transactional
    public Vehicule affecterVehiculeAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));

        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public List<Vehicule> findByAgence(Long agenceId) {
        if (!agenceRepository.existsById(agenceId)) {
            throw new ResourceNotFoundException("Agence", agenceId);
        }
        return vehiculeRepository.findByAgence_IdAgence(agenceId);
    }

    @Override
    public List<Vehicule> findByEquipement(Long equipementId) {
        if (!equipementRepository.existsById(equipementId)) {
            throw new ResourceNotFoundException("Equipement", equipementId);
        }
        return vehiculeRepository.findByEquipements_IdEquipement(equipementId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findEquipements(Long vehiculeId) {
        findById(vehiculeId);
        return equipementRepository.findByVehicules_IdVehicule(vehiculeId);
    }

    @Override
    @Transactional
    public void ajouterEquipement(Long vehiculeId, Long equipementId) {
        Vehicule vehicule = findById(vehiculeId);
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", equipementId));
        vehicule.getEquipements().add(equipement);
        vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void retirerEquipement(Long vehiculeId, Long equipementId) {
        Vehicule vehicule = findById(vehiculeId);
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", equipementId));
        vehicule.getEquipements().remove(equipement);
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void retirerDeAgence(Long agenceId, Long vehiculeId) {
        Vehicule vehicule = findById(vehiculeId);
        if (vehicule.getAgence() == null || !vehicule.getAgence().getIdAgence().equals(agenceId)) {
            throw new ResourceNotFoundException("Association véhicule/agence", vehiculeId);
        }
        throw new ResourceConflictException("Un véhicule doit rester affecté à une agence.");
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void valider(Vehicule e) {
        if (e.getImmatriculation() == null || e.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException("L'immatriculation est obligatoire");
        }
        if (e.getTarifJournalier() == null) {
            throw new IllegalArgumentException("Le tarif journalier est obligatoire");
        }
        if (e.getTarifJournalier().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le tarif journalier doit être strictement positif");
        }
    }
}
