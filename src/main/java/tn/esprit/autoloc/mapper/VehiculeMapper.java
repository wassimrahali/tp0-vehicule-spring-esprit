package tn.esprit.autoloc.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Component
@RequiredArgsConstructor
public class VehiculeMapper {
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IVehiculeRepository vehiculeRepository;

    public VehiculeDTO toDto(Vehicule entity) {
        return new VehiculeDTO(entity.getIdVehicule(), entity.getImmatriculation(), entity.getMarque(),
                entity.getModele(), entity.getCategorie(), entity.getTarifJournalier(), entity.getStatut(),
                entity.getAgence() == null ? null : entity.getAgence().getIdAgence(),
                vehiculeRepository.findEquipementIds(entity.getIdVehicule()),
                vehiculeRepository.findMaintenanceIds(entity.getIdVehicule()),
                vehiculeRepository.findReservationIds(entity.getIdVehicule()));
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        Vehicule entity = new Vehicule();
        entity.setIdVehicule(dto.idVehicule());
        entity.setImmatriculation(dto.immatriculation());
        entity.setMarque(dto.marque());
        entity.setModele(dto.modele());
        entity.setCategorie(dto.categorie());
        entity.setTarifJournalier(dto.tarifJournalier());
        entity.setStatut(dto.statut());
        entity.setAgence(agenceRepository.findById(dto.agenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId())));
        if (dto.equipementIds() != null) {
            if (dto.equipementIds().stream().anyMatch(java.util.Objects::isNull)) {
                throw new IllegalArgumentException("Un identifiant d'équipement ne peut pas être nul.");
            }
            var equipements = equipementRepository.findAllById(dto.equipementIds());
            if (equipements.size() != dto.equipementIds().stream().distinct().count()) {
                throw new ResourceNotFoundException("Equipement", dto.equipementIds().stream()
                        .filter(id -> equipements.stream().noneMatch(e -> e.getIdEquipement().equals(id)))
                        .findFirst().orElse(null));
            }
            entity.setEquipements(new java.util.HashSet<>(equipements));
        }
        return entity;
    }
}
