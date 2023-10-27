package ar.edu.itba.paw.webapp.annotations;


import ar.edu.itba.paw.webapp.validators.NewMainImageValidator;
import ar.edu.itba.paw.webapp.validators.QuantityArrayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewMainImageValidator.class)
public @interface ValidNewMainImage {
    String message() default "The new main image must weight less than 10MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
