package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "EquipementDTO", example = "{\"libelle\":\"Bluetooth\"}")
public record EquipementDTO(Long idEquipement, @NotBlank String libelle, List<Long> vehiculeIds) {
}
