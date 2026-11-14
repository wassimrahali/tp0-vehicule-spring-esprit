package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratService {

    Contrat create(Contrat entite);

    Contrat findById(Long id);

    List<Contrat> findAll();

    Contrat update(Long id, Contrat entite);

    Contrat findByReservation(Long reservationId);

    void deleteById(Long id);
}
