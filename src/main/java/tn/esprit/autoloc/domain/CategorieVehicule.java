package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Catégorie du véhicule", allowableValues = {"CITADINE", "BERLINE", "SUV", "UTILITAIRE"})
public enum CategorieVehicule {
    CITADINE, BERLINE, SUV, UTILITAIRE
}
