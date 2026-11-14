package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(name = "ClientDTO", example = "{\"nom\":\"Ben Ali\",\"prenom\":\"Sami\",\"email\":\"sami@example.com\",\"telephone\":\"+216 20 000 000\",\"numPermis\":\"12345678\",\"dateInscription\":\"2026-11-14\"}")
public record ClientDTO(
        Long idClient,
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotBlank @Email String email,
        String telephone,
        String numPermis,
        LocalDate dateInscription,
        List<Long> reservationIds) {
}
