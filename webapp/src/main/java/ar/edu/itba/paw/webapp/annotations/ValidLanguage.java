package ar.edu.itba.paw.webapp.annotations;


import ar.edu.itba.paw.webapp.validators.LanguageValidator;
import ar.edu.itba.paw.webapp.validators.MeasureIdArrayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguageValidator.class)
public @interface ValidLanguage {


    String message() default "It is an invalid language";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
