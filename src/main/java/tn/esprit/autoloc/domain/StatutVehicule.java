package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Statut du véhicule", allowableValues = {"DISPONIBLE", "LOUE", "MAINTENANCE"})
public enum StatutVehicule {
    DISPONIBLE, LOUE, MAINTENANCE
}
