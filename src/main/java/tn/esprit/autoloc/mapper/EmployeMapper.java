package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface EmployeMapper {
    @Mapping(target = "agenceId", source = "agence.idAgence")
    EmployeDTO toDto(Employe entity);

    @Mapping(target = "agence", source = "agenceId")
    Employe toEntity(EmployeDTO dto);
}
