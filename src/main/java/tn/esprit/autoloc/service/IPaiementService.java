package tn.esprit.autoloc.service;

import java.util.List;
import java.math.BigDecimal;
import tn.esprit.autoloc.dto.PaiementDTO;

public interface IPaiementService {

    PaiementDTO findById(Long id);

    List<PaiementDTO> findAll();

    PaiementDTO create(PaiementDTO paiement);

    PaiementDTO createForContrat(Long contratId, PaiementDTO paiement);

    PaiementDTO update(Long id, PaiementDTO paiement);

    void deleteById(Long id);

    List<PaiementDTO> findByContrat(Long contratId);

    BigDecimal totalForContrat(Long contratId);

    boolean contratTotalementPaye(Long contratId);
}
