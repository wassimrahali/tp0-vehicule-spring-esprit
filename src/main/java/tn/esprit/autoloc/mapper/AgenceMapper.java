package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface AgenceMapper {
    @Mapping(target = "vehiculeIds", source = "vehicules")
    @Mapping(target = "employeIds", source = "employes")
    AgenceDTO toDto(Agence entity);

    @Mapping(target = "vehicules", source = "vehiculeIds")
    @Mapping(target = "employes", source = "employeIds")
    Agence toEntity(AgenceDTO dto);
}
