package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import tn.esprit.autoloc.domain.RoleEmploye;

@Schema(name = "EmployeDTO", example = "{\"nom\":\"Khaled\",\"prenom\":\"Mourad\",\"role\":\"AGENT\",\"agenceId\":1}")
public record EmployeDTO(
        Long idEmploye,
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotNull RoleEmploye role,
        @NotNull Long agenceId) {
}
