package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.unit.Unit;

import java.util.List;

public interface UnitsDao {
    List<Unit> getAllUnits();

    List<Unit> getUnitsById(Long[] ids);
}
