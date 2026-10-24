package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mode de paiement", allowableValues = {"CARTE", "ESPECES", "VIREMENT"})
public enum ModePaiement {
    CARTE, ESPECES, VIREMENT
}
