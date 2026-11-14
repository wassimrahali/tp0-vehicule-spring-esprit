package tn.esprit.autoloc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementDTO;

@Mapper(componentModel = "spring", uses = EntityReferenceMapper.class)
public interface PaiementMapper {
    @Mapping(target = "contratId", source = "contrat.idContrat")
    PaiementDTO toDto(Paiement entity);

    @Mapping(target = "contrat", source = "contratId")
    Paiement toEntity(PaiementDTO dto);
}
