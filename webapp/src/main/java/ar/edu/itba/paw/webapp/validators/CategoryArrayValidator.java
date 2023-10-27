package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.servicesInterface.category.CategoryService;
import ar.edu.itba.paw.webapp.annotations.ValidCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class CategoryArrayValidator implements ConstraintValidator<ValidCategories, Long[]> {

    private final Set<Long> categoryIds;


    @Autowired
    public CategoryArrayValidator(CategoryService cs) {
        categoryIds = new HashSet<>();
        cs.getAllCategories().forEach(category -> categoryIds.add(category.getCategoryId()));
    }

    @Override
    public boolean isValid(Long[] categorys, ConstraintValidatorContext constraintValidatorContext) {
        if (categorys == null || categorys.length == 0) {
            return false;
        }

        return Arrays.stream(categorys).filter(Objects::nonNull).allMatch(categoryIds::contains);
    }
}
