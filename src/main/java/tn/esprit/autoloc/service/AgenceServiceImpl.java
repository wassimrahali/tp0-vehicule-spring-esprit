package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper mapper;

    @Override
    public AgenceDTO create(AgenceDTO dto) {
        Agence entite = mapper.toEntity(dto);
        if (entite.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau agence ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return mapper.toDto(agenceRepository.save(entite));
    }

    @Override
    public AgenceDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Agence findEntity(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<AgenceDTO> findAll() {
        return agenceRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public AgenceDTO update(Long id, AgenceDTO dto) {
        Agence entite = mapper.toEntity(dto);
        Agence existant = findEntity(id);
        valider(entite);
        existant.setNom(entite.getNom());
        existant.setVille(entite.getVille());
        existant.setAdresse(entite.getAdresse());
        existant.setTelephone(entite.getTelephone());
        return mapper.toDto(agenceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private void valider(Agence e) {
        if (e.getNom() == null || e.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
    }
}