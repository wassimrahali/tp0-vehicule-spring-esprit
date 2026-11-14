package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Maintenance create(Maintenance entite) {
        if (entite.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau maintenance ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return maintenanceRepository.save(entite);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance entite) {
        Maintenance existant = findById(id);
        valider(entite);
        existant.setDateDebut(entite.getDateDebut());
        existant.setDateFin(entite.getDateFin());
        existant.setDescription(entite.getDescription());
        existant.setVehicule(entite.getVehicule());
        return maintenanceRepository.save(existant);
    }

    @Override
    public List<Maintenance> findByVehicule(Long vehiculeId) {
        if (!vehiculeRepository.existsById(vehiculeId)) {
            throw new ResourceNotFoundException("Vehicule", vehiculeId);
        }
        return maintenanceRepository.findByVehicule_IdVehicule(vehiculeId);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void valider(Maintenance e) {
        if (e.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début est obligatoire");
        }
        if (e.getDateFin() != null && !e.getDateFin().isAfter(e.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début");
        }
        if (e.getVehicule() == null) {
            throw new IllegalArgumentException("La maintenance doit être liée à un véhicule");
        }
    }
}
