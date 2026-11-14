package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientDTO;
import tn.esprit.autoloc.repository.IClientRepository;

@Component
public class ClientMapper {
    private final IClientRepository clientRepository;

    public ClientMapper(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO toDto(Client entity) {
        return new ClientDTO(entity.getIdClient(), entity.getNom(), entity.getPrenom(), entity.getEmail(),
                entity.getTelephone(), entity.getNumPermis(), entity.getDateInscription(),
                clientRepository.findReservationIds(entity.getIdClient()));
    }

    public Client toEntity(ClientDTO dto) {
        Client entity = new Client();
        entity.setIdClient(dto.idClient());
        entity.setNom(dto.nom());
        entity.setPrenom(dto.prenom());
        entity.setEmail(dto.email());
        entity.setTelephone(dto.telephone());
        entity.setNumPermis(dto.numPermis());
        entity.setDateInscription(dto.dateInscription());
        return entity;
    }
}
