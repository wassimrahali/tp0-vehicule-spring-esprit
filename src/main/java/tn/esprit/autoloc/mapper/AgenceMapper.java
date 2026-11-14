package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Component
public class AgenceMapper {
    private final IAgenceRepository agenceRepository;

    public AgenceMapper(IAgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public AgenceDTO toDto(Agence entity) {
        return new AgenceDTO(entity.getIdAgence(), entity.getNom(), entity.getVille(),
                entity.getAdresse(), entity.getTelephone(),
                agenceRepository.findVehiculeIds(entity.getIdAgence()),
                agenceRepository.findEmployeIds(entity.getIdAgence()));
    }

    public Agence toEntity(AgenceDTO dto) {
        Agence entity = new Agence();
        entity.setIdAgence(dto.idAgence());
        entity.setNom(dto.nom());
        entity.setVille(dto.ville());
        entity.setAdresse(dto.adresse());
        entity.setTelephone(dto.telephone());
        return entity;
    }
}
