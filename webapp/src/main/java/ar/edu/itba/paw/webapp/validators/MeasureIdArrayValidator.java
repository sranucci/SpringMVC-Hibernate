package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.models.unit.Unit;
import ar.edu.itba.paw.servicesInterface.unit.UnitsService;
import ar.edu.itba.paw.webapp.annotations.ValidMeasureIdArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;


@Component
public class MeasureIdArrayValidator implements ConstraintValidator<ValidMeasureIdArray, Long[]> {


    Set<Long> ids;


    @Autowired
    public MeasureIdArrayValidator(UnitsService us) {
        List<Unit> l = us.getAllUnits();
        ids = new HashSet<>();
        l.forEach(unit -> ids.add(unit.getId()));
    }


    @Override
    public boolean isValid(Long[] units, ConstraintValidatorContext constraintValidatorContext) {
        if (units == null || units.length == 0) {
            return false;
        }
        return Arrays.stream(units).filter(Objects::nonNull).allMatch(ids::contains);
    }
}
