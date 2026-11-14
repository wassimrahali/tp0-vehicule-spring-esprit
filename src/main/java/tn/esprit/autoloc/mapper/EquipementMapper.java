package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface EquipementMapper {
    @Mapping(target = "vehiculeIds", source = "vehicules")
    EquipementDTO toDto(Equipement entity);

    @Mapping(target = "vehicules", source = "vehiculeIds")
    Equipement toEntity(EquipementDTO dto);
}
