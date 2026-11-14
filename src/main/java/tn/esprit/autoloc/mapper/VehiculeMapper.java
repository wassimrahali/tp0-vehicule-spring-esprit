package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface VehiculeMapper {
    @Mapping(target = "agenceId", source = "agence.idAgence")
    @Mapping(target = "equipementIds", source = "equipements")
    @Mapping(target = "maintenanceIds", source = "maintenances")
    @Mapping(target = "reservationIds", source = "reservations")
    VehiculeDTO toDto(Vehicule entity);

    @Mapping(target = "agence", source = "agenceId")
    @Mapping(target = "equipements", source = "equipementIds")
    @Mapping(target = "maintenances", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Vehicule toEntity(VehiculeDTO dto);
}
