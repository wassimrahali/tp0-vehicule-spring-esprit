package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "AgenceDTO", example = "{\"nom\":\"AutoLoc Center\",\"ville\":\"Tunis\",\"adresse\":\"Avenue Habib Bourguiba 12\",\"telephone\":\"+216 71 000 000\"}")
public record AgenceDTO(
        Long idAgence,
        @NotBlank String nom,
        String ville,
        String adresse,
        String telephone,
        List<Long> vehiculeIds,
        List<Long> employeIds) {
}
