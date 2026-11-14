package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import tn.esprit.autoloc.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(name = "PaiementDTO", example = "{\"montant\":150.00,\"datePaiement\":\"2026-12-02\",\"modePaiement\":\"CARTE\",\"contratId\":1}")
public record PaiementDTO(
        Long idPaiement,
        @NotNull @Positive BigDecimal montant,
        @NotNull LocalDate datePaiement,
        @NotNull ModePaiement modePaiement,
        @NotNull Long contratId) {
}
