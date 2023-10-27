package ar.edu.itba.paw.webapp.annotations;


import ar.edu.itba.paw.webapp.validators.HoursAndMinutesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HoursAndMinutesValidator.class)
public @interface ValidHoursAndMinutes {

    String message() default "The recipe must at least have a duration in minutes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
