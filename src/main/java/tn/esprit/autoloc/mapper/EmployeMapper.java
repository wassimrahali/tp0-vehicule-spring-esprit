package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Component
@RequiredArgsConstructor
public class EmployeMapper {
    private final IAgenceRepository agenceRepository;

    public EmployeDTO toDto(Employe entity) {
        return new EmployeDTO(entity.getIdEmploye(), entity.getNom(), entity.getPrenom(),
                entity.getRole(), entity.getAgence() == null ? null : entity.getAgence().getIdAgence());
    }

    public Employe toEntity(EmployeDTO dto) {
        Employe entity = new Employe();
        entity.setIdEmploye(dto.idEmploye());
        entity.setNom(dto.nom());
        entity.setPrenom(dto.prenom());
        entity.setRole(dto.role());
        entity.setAgence(agenceRepository.findById(dto.agenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId())));
        return entity;
    }
}
