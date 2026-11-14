package tn.esprit.autoloc.service;

import java.util.List;
import java.math.BigDecimal;
import tn.esprit.autoloc.domain.Paiement;

public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();

    Paiement create(Paiement paiement);

    Paiement createForContrat(Long contratId, Paiement paiement);

    Paiement update(Long id, Paiement paiement);

    void deleteById(Long id);

    List<Paiement> findByContrat(Long contratId);

    BigDecimal totalForContrat(Long contratId);

    boolean contratTotalementPaye(Long contratId);
}
