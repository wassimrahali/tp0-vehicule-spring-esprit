package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface ClientMapper {
    @Mapping(target = "reservationIds", source = "reservations")
    ClientDTO toDto(Client entity);

    @Mapping(target = "reservations", source = "reservationIds")
    Client toEntity(ClientDTO dto);
}
