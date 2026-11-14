package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface MaintenanceMapper {
    @Mapping(target = "vehiculeId", source = "vehicule.idVehicule")
    MaintenanceDTO toDto(Maintenance entity);

    @Mapping(target = "vehicule", source = "vehiculeId")
    Maintenance toEntity(MaintenanceDTO dto);
}
