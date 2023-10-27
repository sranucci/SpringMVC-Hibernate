package ar.edu.itba.paw.services.unit;

import ar.edu.itba.paw.models.unit.Unit;
import ar.edu.itba.paw.servicesInterface.unit.UnitsService;
import ar.edu.itba.persistenceInterface.UnitsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnitsServiceImpl implements UnitsService {

    private final UnitsDao unitsDao;

    @Autowired
    public UnitsServiceImpl(final UnitsDao unitsDao) {
        this.unitsDao = unitsDao;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Unit> getAllUnits() {
        return unitsDao.getAllUnits();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Unit> getUnitsById(Long[] ids) {
        return unitsDao.getUnitsById(ids);
    }


}
