package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

@Schema(name = "ReservationDTO", example = "{\"dateDebut\":\"2026-12-01\",\"dateFin\":\"2026-12-04\",\"statut\":\"EN_ATTENTE\",\"clientId\":1,\"vehiculeId\":2}")
public record ReservationDTO(
        Long idReservation,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @NotNull StatutReservation statut,
        @NotNull Long clientId,
        @NotNull Long vehiculeId,
        Long contratId) {
}
