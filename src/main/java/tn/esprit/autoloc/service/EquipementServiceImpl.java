package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper mapper;

    @Override
    public EquipementDTO create(EquipementDTO dto) {
        Equipement entite = mapper.toEntity(dto);
        if (entite.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau equipement ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return mapper.toDto(equipementRepository.save(entite));
    }

    @Override
    public EquipementDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Equipement findEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<EquipementDTO> findAll() {
        return equipementRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public EquipementDTO update(Long id, EquipementDTO dto) {
        Equipement entite = mapper.toEntity(dto);
        Equipement existant = findEntity(id);
        valider(entite);
        existant.setLibelle(entite.getLibelle());
        return mapper.toDto(equipementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private void valider(Equipement e) {
        if (e.getLibelle() == null || e.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }
    }
}
