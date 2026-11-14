package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper mapper;

    @Override
    public MaintenanceDTO create(MaintenanceDTO dto) {
        Maintenance entite = mapper.toEntity(dto);
        if (entite.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau maintenance ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return mapper.toDto(maintenanceRepository.save(entite));
    }

    @Override
    public MaintenanceDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Maintenance findEntity(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<MaintenanceDTO> findAll() {
        return maintenanceRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {
        Maintenance entite = mapper.toEntity(dto);
        Maintenance existant = findEntity(id);
        valider(entite);
        existant.setDateDebut(entite.getDateDebut());
        existant.setDateFin(entite.getDateFin());
        existant.setDescription(entite.getDescription());
        existant.setVehicule(entite.getVehicule());
        return mapper.toDto(maintenanceRepository.save(existant));
    }

    @Override
    public List<MaintenanceDTO> findByVehicule(Long vehiculeId) {
        if (!vehiculeRepository.existsById(vehiculeId)) {
            throw new ResourceNotFoundException("Vehicule", vehiculeId);
        }
        return maintenanceRepository.findByVehicule_IdVehicule(vehiculeId).stream().map(mapper::toDto).toList();
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
