package ar.edu.itba.paw.webapp.annotations;


import ar.edu.itba.paw.webapp.validators.UserPhotoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPhotoValidator.class)
public @interface ValidUserPhoto {
    String message() default "ValidUserPhoto.editProfileForm.userPhoto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
