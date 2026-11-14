package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Component
@RequiredArgsConstructor
public class MaintenanceMapper {
    private final IVehiculeRepository vehiculeRepository;

    public MaintenanceDTO toDto(Maintenance entity) {
        return new MaintenanceDTO(entity.getIdMaintenance(), entity.getDateDebut(), entity.getDateFin(),
                entity.getDescription(), entity.getVehicule() == null ? null : entity.getVehicule().getIdVehicule());
    }

    public Maintenance toEntity(MaintenanceDTO dto) {
        Maintenance entity = new Maintenance();
        entity.setIdMaintenance(dto.idMaintenance());
        entity.setDateDebut(dto.dateDebut());
        entity.setDateFin(dto.dateFin());
        entity.setDescription(dto.description());
        entity.setVehicule(vehiculeRepository.findById(dto.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.vehiculeId())));
        return entity;
    }
}
