package tn.esprit.autoloc.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.mapper.AgenceMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IContratRepository contratRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper mapper;
    private final EquipementMapper equipementMapper;
    private final AgenceMapper agenceMapper;

    @Override
    public VehiculeDTO create(VehiculeDTO dto) {
        Vehicule entite = mapper.toEntity(dto);
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
        return mapper.toDto(vehiculeRepository.save(entite));
    }

    @Override
    public VehiculeDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Vehicule findEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeDTO dto) {
        Vehicule entite = mapper.toEntity(dto);
        Vehicule existant = findEntity(id);
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
        return mapper.toDto(vehiculeRepository.save(existant));
    }

    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));

        vehicule.setAgence(agence);
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public AgenceDTO findAgence(Long vehiculeId) {
        Vehicule vehicule = findEntity(vehiculeId);
        if (vehicule.getAgence() == null) {
            throw new ResourceNotFoundException("Agence du véhicule", vehiculeId);
        }
        return agenceMapper.toDto(vehicule.getAgence());
    }

    @Override
    public List<VehiculeDTO> findByAgence(Long agenceId) {
        if (!agenceRepository.existsById(agenceId)) {
            throw new ResourceNotFoundException("Agence", agenceId);
        }
        return vehiculeRepository.findByAgence_IdAgence(agenceId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<VehiculeDTO> findByEquipement(Long equipementId) {
        if (!equipementRepository.existsById(equipementId)) {
            throw new ResourceNotFoundException("Equipement", equipementId);
        }
        return vehiculeRepository.findByEquipements_IdEquipement(equipementId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<VehiculeDTO> findByContrat(Long contratId) {
        if (!contratRepository.existsById(contratId)) {
            throw new ResourceNotFoundException("Contrat", contratId);
        }
        return vehiculeRepository.findByReservations_Contrat_IdContrat(contratId).stream()
                .map(mapper::toDto).toList();
    }

    @Override
    public List<VehiculeDTO> findByMaintenance(Long maintenanceId) {
        if (!maintenanceRepository.existsById(maintenanceId)) {
            throw new ResourceNotFoundException("Maintenance", maintenanceId);
        }
        return vehiculeRepository.findByMaintenances_IdMaintenance(maintenanceId).stream()
                .map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> findEquipements(Long vehiculeId) {
        findEntity(vehiculeId);
        return equipementRepository.findByVehicules_IdVehicule(vehiculeId).stream()
                .map(equipementMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void ajouterEquipement(Long vehiculeId, Long equipementId) {
        Vehicule vehicule = findEntity(vehiculeId);
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", equipementId));
        vehicule.getEquipements().add(equipement);
        vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void retirerEquipement(Long vehiculeId, Long equipementId) {
        Vehicule vehicule = findEntity(vehiculeId);
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", equipementId));
        vehicule.getEquipements().remove(equipement);
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void retirerDeAgence(Long agenceId, Long vehiculeId) {
        Vehicule vehicule = findEntity(vehiculeId);
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
