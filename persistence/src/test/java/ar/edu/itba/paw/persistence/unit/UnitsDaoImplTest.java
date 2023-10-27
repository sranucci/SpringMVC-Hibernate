package ar.edu.itba.paw.persistence.unit;


import ar.edu.itba.paw.models.unit.Unit;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.UnitsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateUnitsDaoImplTest.sql"})
public class UnitsDaoImplTest {

    @Autowired
    private UnitsDao unitsDao;

    @Test
    public void testGetAllUnits(){
        List<Unit> unitList = unitsDao.getAllUnits();
        assertEquals(2, unitList.size());
        assertEquals("g", unitList.get(0).getUnitName());
        assertEquals("ml", unitList.get(1).getUnitName());
    }

    @Test
    public void testGetAllUnitsByIds(){
        List<Unit> unitList = unitsDao.getUnitsById(new Long[]{1L,2L});
        assertEquals(2, unitList.size());
        assertEquals("g", unitList.get(0).getUnitName());
        assertEquals("ml", unitList.get(1).getUnitName());
    }


}


