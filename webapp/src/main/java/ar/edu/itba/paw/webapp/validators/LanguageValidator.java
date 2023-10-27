package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidLanguage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LanguageValidator implements ConstraintValidator<ValidLanguage, String> {
    @Override
    public boolean isValid(String language, ConstraintValidatorContext constraintValidatorContext) {
        if ( language == null)
            return false;
        return language.equals("en") || language.equals("es");
    }
}
