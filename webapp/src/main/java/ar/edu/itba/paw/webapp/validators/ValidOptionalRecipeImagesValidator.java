package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidOptionalImages;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidOptionalRecipeImagesValidator implements ConstraintValidator<ValidOptionalImages, List<MultipartFile>> {

    private static final int MAXSIZE = 10 * 1024 * 1024;//10MB

    @Override
    public boolean isValid(List<MultipartFile> imageList, ConstraintValidatorContext constraintValidatorContext) {

        if (imageList == null || imageList.isEmpty())
            return true;

        return imageList.stream()
                .allMatch(image -> image != null
                        && (image.isEmpty() || StringUtils.startsWithIgnoreCase(image.getContentType(), "image/jpeg"))
                        && image.getSize() <= MAXSIZE
                );

    }
}
