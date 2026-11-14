package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface ContratMapper {
    @Mapping(target = "reservationId", source = "reservation.idReservation")
    @Mapping(target = "paiementIds", source = "paiements")
    ContratDTO toDto(Contrat entity);

    @Mapping(target = "reservation", source = "reservationId")
    @Mapping(target = "paiements", source = "paiementIds")
    Contrat toEntity(ContratDTO dto);
}
