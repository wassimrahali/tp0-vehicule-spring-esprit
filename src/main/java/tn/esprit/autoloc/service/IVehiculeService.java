package tn.esprit.autoloc.service;

import java.util.List;
import java.util.List;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeService {

    Vehicule create(Vehicule entite);

    Vehicule findById(Long id);

    List<Vehicule> findAll();

    Vehicule update(Long id, Vehicule entite);

    Vehicule affecterVehiculeAAgence(Long idVehicule, Long idAgence);

    List<Vehicule> findByAgence(Long agenceId);

    List<Vehicule> findByEquipement(Long equipementId);

    List<Equipement> findEquipements(Long vehiculeId);

    void ajouterEquipement(Long vehiculeId, Long equipementId);

    void retirerEquipement(Long vehiculeId, Long equipementId);

    void retirerDeAgence(Long agenceId, Long vehiculeId);

    void deleteById(Long id);
}
