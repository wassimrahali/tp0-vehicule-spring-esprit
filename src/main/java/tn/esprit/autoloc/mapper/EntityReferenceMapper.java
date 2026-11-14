package tn.esprit.autoloc.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EntityReferenceMapper {
    @PersistenceContext
    private EntityManager entityManager;

    public Agence toAgence(Long id) {
        return reference(id, Agence.class, "Agence");
    }

    public Employe toEmploye(Long id) {
        return reference(id, Employe.class, "Employe");
    }

    public Vehicule toVehicule(Long id) {
        return reference(id, Vehicule.class, "Vehicule");
    }

    public Equipement toEquipement(Long id) {
        return reference(id, Equipement.class, "Equipement");
    }

    public Maintenance toMaintenance(Long id) {
        return reference(id, Maintenance.class, "Maintenance");
    }

    public Reservation toReservation(Long id) {
        return reference(id, Reservation.class, "Reservation");
    }

    public Client toClient(Long id) {
        return reference(id, Client.class, "Client");
    }

    public Contrat toContrat(Long id) {
        return reference(id, Contrat.class, "Contrat");
    }

    public Paiement toPaiement(Long id) {
        return reference(id, Paiement.class, "Paiement");
    }

    public Long map(Agence entity) { return entity == null ? null : entity.getIdAgence(); }
    public Long map(Employe entity) { return entity == null ? null : entity.getIdEmploye(); }
    public Long map(Vehicule entity) { return entity == null ? null : entity.getIdVehicule(); }
    public Long map(Equipement entity) { return entity == null ? null : entity.getIdEquipement(); }
    public Long map(Maintenance entity) { return entity == null ? null : entity.getIdMaintenance(); }
    public Long map(Reservation entity) { return entity == null ? null : entity.getIdReservation(); }
    public Long map(Client entity) { return entity == null ? null : entity.getIdClient(); }
    public Long map(Contrat entity) { return entity == null ? null : entity.getIdContrat(); }
    public Long map(Paiement entity) { return entity == null ? null : entity.getIdPaiement(); }

    private <T> T reference(Long id, Class<T> type, String name) {
        if (id == null) {
            return null;
        }
        T entity = entityManager.find(type, id);
        if (entity == null) {
            throw new ResourceNotFoundException(name, id);
        }
        return entity;
    }
}
