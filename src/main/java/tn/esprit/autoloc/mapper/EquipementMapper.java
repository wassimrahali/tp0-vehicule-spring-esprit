package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Component
public class EquipementMapper {
    private final IEquipementRepository equipementRepository;

    public EquipementMapper(IEquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    public EquipementDTO toDto(Equipement entity) {
        return new EquipementDTO(entity.getIdEquipement(), entity.getLibelle(),
                equipementRepository.findVehiculeIds(entity.getIdEquipement()));
    }

    public Equipement toEntity(EquipementDTO dto) {
        Equipement entity = new Equipement();
        entity.setIdEquipement(dto.idEquipement());
        entity.setLibelle(dto.libelle());
        return entity;
    }
}
