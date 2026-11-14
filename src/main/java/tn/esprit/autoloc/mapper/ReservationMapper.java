package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface ReservationMapper {
    @Mapping(target = "clientId", source = "client.idClient")
    @Mapping(target = "vehiculeId", source = "vehicule.idVehicule")
    @Mapping(target = "contratId", source = "contrat.idContrat")
    ReservationDTO toDto(Reservation entity);

    @Mapping(target = "client", source = "clientId")
    @Mapping(target = "vehicule", source = "vehiculeId")
    @Mapping(target = "contrat", source = "contratId")
    Reservation toEntity(ReservationDTO dto);
}
