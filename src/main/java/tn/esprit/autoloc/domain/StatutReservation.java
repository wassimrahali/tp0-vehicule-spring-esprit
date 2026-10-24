package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Statut de la réservation", allowableValues = {"EN_ATTENTE", "CONFIRMEE", "ANNULEE", "TERMINEE"})
public enum StatutReservation {
    EN_ATTENTE, CONFIRMEE, ANNULEE, TERMINEE
}
