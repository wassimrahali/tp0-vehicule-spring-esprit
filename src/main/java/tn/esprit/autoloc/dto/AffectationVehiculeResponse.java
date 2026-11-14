package tn.esprit.autoloc.dto;

public record AffectationVehiculeResponse(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        Long idAgence,
        String nomAgence) {
}
