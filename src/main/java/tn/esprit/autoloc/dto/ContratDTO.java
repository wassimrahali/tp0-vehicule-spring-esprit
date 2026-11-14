package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "ContratDTO", example = "{\"dateSignature\":\"2026-12-01\",\"montantTotal\":450.00,\"valide\":true,\"reservationId\":1}")
public record ContratDTO(
        Long idContrat,
        LocalDate dateSignature,
        @NotNull @Positive BigDecimal montantTotal,
        boolean valide,
        @NotNull Long reservationId,
        List<Long> paiementIds) {
}
