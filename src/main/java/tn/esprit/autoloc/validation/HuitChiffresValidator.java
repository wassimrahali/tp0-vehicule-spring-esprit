package tn.esprit.autoloc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HuitChiffresValidator implements ConstraintValidator<HuitChiffres, String> {

    @Override
    public boolean isValid(String valeur, ConstraintValidatorContext context) {
        if (valeur == null || valeur.isEmpty()) {
            return true;
        }
        if (valeur.length() != 8) {
            return false;
        }
        for (int i = 0; i < valeur.length(); i++) {
            if (valeur.charAt(i) < '0' || valeur.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
