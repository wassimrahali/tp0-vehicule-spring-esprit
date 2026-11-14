package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper mapper;

    @Override
    public ClientDTO create(ClientDTO dto) {
        Client entite = mapper.toEntity(dto);
        if (entite.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant");
        }
        valider(entite);
        if (clientRepository.existsByEmail(entite.getEmail())) {
            throw new ResourceConflictException("Cette adresse e-mail est déjà utilisée.");
        }
        return mapper.toDto(clientRepository.save(entite));
    }

    @Override
    public ClientDTO findById(Long id) {
        return mapper.toDto(findEntity(id));
    }

    private Client findEntity(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ClientDTO update(Long id, ClientDTO dto) {
        Client entite = mapper.toEntity(dto);
        Client existant = findEntity(id);
        valider(entite);
        if (clientRepository.existsByEmailAndIdClientNot(entite.getEmail(), id)) {
            throw new ResourceConflictException("Cette adresse e-mail est déjà utilisée.");
        }
        existant.setNom(entite.getNom());
        existant.setPrenom(entite.getPrenom());
        existant.setEmail(entite.getEmail());
        existant.setTelephone(entite.getTelephone());
        existant.setNumPermis(entite.getNumPermis());
        existant.setDateInscription(entite.getDateInscription());
        return mapper.toDto(clientRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private void valider(Client e) {
        if (e.getNom() == null || e.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (e.getEmail() == null || e.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'e-mail du client est obligatoire");
        }
    }
}
