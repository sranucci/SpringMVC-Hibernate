package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidMainImage;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MainImageValidator implements ConstraintValidator<ValidMainImage, MultipartFile> {

    private static final int MAXSIZE = 10 * 1024 * 1024;//10MB

    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        if ( image == null || image.isEmpty() )
            return false;

        return StringUtils.startsWithIgnoreCase(image.getContentType(),"image/jpeg") && image.getSize() <= MAXSIZE;

    }
}
