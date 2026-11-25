package tn.esprit.autoloc.service;

import java.util.List;
import java.util.List;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.dto.AgenceDTO;

public interface IVehiculeService {

    VehiculeDTO create(VehiculeDTO entite);

    VehiculeDTO findById(Long id);

    List<VehiculeDTO> findAll();

    VehiculeDTO update(Long id, VehiculeDTO entite);

    VehiculeDTO affecterVehiculeAAgence(Long idVehicule, Long idAgence);

    AgenceDTO findAgence(Long vehiculeId);

    List<VehiculeDTO> findByAgence(Long agenceId);

    List<VehiculeDTO> findByEquipement(Long equipementId);

    List<VehiculeDTO> findByContrat(Long contratId);

    List<VehiculeDTO> findByMaintenance(Long maintenanceId);

    List<EquipementDTO> findEquipements(Long vehiculeId);

    void ajouterEquipement(Long vehiculeId, Long equipementId);

    void retirerEquipement(Long vehiculeId, Long equipementId);

    void retirerDeAgence(Long agenceId, Long vehiculeId);

    void deleteById(Long id);
}
