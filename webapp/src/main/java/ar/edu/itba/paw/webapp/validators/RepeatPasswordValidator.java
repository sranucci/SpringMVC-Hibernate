package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.RepeatPasswordMatchesPassword;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RepeatPasswordValidator implements ConstraintValidator<RepeatPasswordMatchesPassword, Object> {

    private String passwordField;
    private String repeatPasswordField;

    @Override
    public void initialize(RepeatPasswordMatchesPassword constraintAnnotation) {
        passwordField = constraintAnnotation.passwordField();
        repeatPasswordField = constraintAnnotation.repeatPasswordField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Object password = new BeanWrapperImpl(object).getPropertyValue(passwordField);
        Object repeatPassword = new BeanWrapperImpl(object).getPropertyValue(repeatPasswordField);

        return password == null && repeatPassword == null || password != null && password.equals(repeatPassword);
    }
}
