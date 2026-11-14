package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.ContratDTO;

public interface IContratService {

    ContratDTO create(ContratDTO entite);

    ContratDTO findById(Long id);

    List<ContratDTO> findAll();

    ContratDTO update(Long id, ContratDTO entite);

    ContratDTO findByReservation(Long reservationId);

    void deleteById(Long id);
}
