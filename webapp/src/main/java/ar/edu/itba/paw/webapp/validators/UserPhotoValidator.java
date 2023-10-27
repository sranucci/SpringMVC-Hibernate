package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidUserPhoto;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserPhotoValidator implements ConstraintValidator<ValidUserPhoto, MultipartFile> {
    private static final int MAXSIZE = 10 * 1024 * 1024;//10MB

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (!multipartFile.isEmpty()) {
            return multipartFile.getSize() <= MAXSIZE && StringUtils.startsWithIgnoreCase(multipartFile.getContentType(), "image/jpeg");
        }
        return true;
    }
}
