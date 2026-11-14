package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "MaintenanceDTO", example = "{\"dateDebut\":\"2026-11-20\",\"dateFin\":\"2026-11-22\",\"description\":\"Révision périodique\",\"vehiculeId\":1}")
public record MaintenanceDTO(
        Long idMaintenance,
        @NotNull LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        @NotNull Long vehiculeId) {
}
