package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper mapper;

    @Override
    public EmployeDTO create(EmployeDTO dto) {
        Employe entite = mapper.toEntity(dto);
        if (entite.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau employe ne doit pas avoir d'identifiant");
        }
        valider(entite);
        return mapper.toDto(employeRepository.save(entite));
    }

    @Override
    public EmployeDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Employe findEntity(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<EmployeDTO> findAll() {
        return employeRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public EmployeDTO update(Long id, EmployeDTO dto) {
        Employe entite = mapper.toEntity(dto);
        Employe existant = findEntity(id);
        valider(entite);
        existant.setNom(entite.getNom());
        existant.setPrenom(entite.getPrenom());
        existant.setRole(entite.getRole());
        existant.setAgence(entite.getAgence());
        return mapper.toDto(employeRepository.save(existant));
    }

    @Override
    public List<EmployeDTO> findByAgence(Long agenceId) {
        if (!agenceRepository.existsById(agenceId)) {
            throw new ResourceNotFoundException("Agence", agenceId);
        }
        return employeRepository.findByAgence_IdAgence(agenceId).stream().map(mapper::toDto).toList();
    }

    @Override
    public EmployeDTO affecterAgence(Long employeId, Long agenceId) {
        Employe employe = findEntity(employeId);
        employe.setAgence(agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId)));
        return mapper.toDto(employeRepository.save(employe));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void valider(Employe e) {
        if (e.getNom() == null || e.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire");
        }
        if (e.getPrenom() == null || e.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom de l'employé est obligatoire");
        }
        if (e.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'employé est obligatoire");
        }
        if (e.getAgence() == null) {
            throw new IllegalArgumentException("Un employé doit être affecté à une agence");
        }
    }
}
