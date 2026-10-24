package tn.esprit.autoloc.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rôle de l'employé", allowableValues = {"AGENT", "MANAGER"})
public enum RoleEmploye {
    AGENT, MANAGER
}
