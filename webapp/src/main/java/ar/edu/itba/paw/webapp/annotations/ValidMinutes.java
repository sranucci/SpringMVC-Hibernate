package ar.edu.itba.paw.webapp.annotations;


import ar.edu.itba.paw.webapp.validators.MinutesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinutesValidator.class)
public @interface ValidMinutes {
    String message() default "Minutes must be between 0 and 59";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
