package tn.esprit.autoloc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = HuitChiffresValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface HuitChiffres {
    String message() default "doit contenir exactement 8 chiffres, sans symbole";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
