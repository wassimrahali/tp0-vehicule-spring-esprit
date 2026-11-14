package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceConflictException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaiementServiceImplTest {
    @Mock
    private IPaiementRepository paiementRepository;
    @Mock
    private IContratRepository contratRepository;
    @InjectMocks
    private PaiementServiceImpl service;

    @Test
    void refusesPaymentThatWouldExceedContractTotal() {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(5L);
        contrat.setMontantTotal(new BigDecimal("100.00"));
        when(contratRepository.findForUpdate(5L)).thenReturn(Optional.of(contrat));
        when(paiementRepository.totalForContrat(5L)).thenReturn(new BigDecimal("80.00"));

        Paiement paiement = new Paiement();
        paiement.setMontant(new BigDecimal("30.00"));
        paiement.setDatePaiement(LocalDate.of(2026, 11, 14));
        paiement.setModePaiement(ModePaiement.CARTE);
        paiement.setContrat(contrat);

        assertThrows(ResourceConflictException.class, () -> service.create(paiement));
        verify(paiementRepository, never()).save(any());
    }
}
