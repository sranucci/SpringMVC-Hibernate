package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidNewMainImage;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NewMainImageValidator implements ConstraintValidator<ValidNewMainImage, MultipartFile> {
    private static final int MAXSIZE = 10 * 1024 * 1024;//10MB

    @Override
    public boolean isValid(MultipartFile image, ConstraintValidatorContext constraintValidatorContext) {
        return image.isEmpty() || (StringUtils.startsWithIgnoreCase(image.getContentType(),"image/jpeg") && image.getSize() <= MAXSIZE );
    }
}
