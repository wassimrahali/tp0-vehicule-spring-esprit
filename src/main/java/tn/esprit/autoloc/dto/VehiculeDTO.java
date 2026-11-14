package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.media.Schema;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.List;

@Schema(name = "VehiculeDTO", example = "{\"immatriculation\":\"123-TN-4567\",\"marque\":\"Toyota\",\"modele\":\"Corolla\",\"categorie\":\"BERLINE\",\"tarifJournalier\":65.00,\"statut\":\"DISPONIBLE\",\"agenceId\":1,\"equipementIds\":[]}")
public record VehiculeDTO(
        Long idVehicule,
        @NotBlank String immatriculation,
        @NotBlank String marque,
        @NotBlank String modele,
        @NotNull CategorieVehicule categorie,
        @NotNull @Positive BigDecimal tarifJournalier,
        @NotNull StatutVehicule statut,
        @NotNull Long agenceId,
        @NotNull List<Long> equipementIds,
        List<Long> maintenanceIds,
        List<Long> reservationIds) {
}
