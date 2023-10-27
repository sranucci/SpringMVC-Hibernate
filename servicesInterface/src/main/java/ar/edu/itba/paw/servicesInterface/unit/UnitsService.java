package ar.edu.itba.paw.servicesInterface.unit;

import ar.edu.itba.paw.models.unit.Unit;

import java.util.List;

public interface UnitsService {
    List<Unit> getAllUnits();
    List<Unit> getUnitsById(Long[] ids);
}
