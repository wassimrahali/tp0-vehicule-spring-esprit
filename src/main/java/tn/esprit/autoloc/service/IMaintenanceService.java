package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.MaintenanceDTO;

public interface IMaintenanceService {

    MaintenanceDTO create(MaintenanceDTO entite);

    MaintenanceDTO findById(Long id);

    List<MaintenanceDTO> findAll();

    MaintenanceDTO update(Long id, MaintenanceDTO entite);

    List<MaintenanceDTO> findByVehicule(Long vehiculeId);

    void deleteById(Long id);
}
