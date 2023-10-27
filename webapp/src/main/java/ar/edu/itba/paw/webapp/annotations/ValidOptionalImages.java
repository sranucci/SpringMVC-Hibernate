package ar.edu.itba.paw.webapp.annotations;


import ar.edu.itba.paw.webapp.validators.ValidOptionalRecipeImagesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOptionalRecipeImagesValidator.class)
public @interface ValidOptionalImages {
    String message() default "Uploaded images must be jpeg and smaller than 10mb";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
