package ar.edu.itba.paw.persistence.unit;

import ar.edu.itba.paw.models.unit.Unit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
public class UnitsDaoImpl implements ar.edu.itba.persistenceInterface.UnitsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Unit> getAllUnits() {
        return em.createQuery("SELECT u FROM Unit u", Unit.class)
                .getResultList();
    }

    @Override
    public List<Unit> getUnitsById(Long[] ids) {
        return em.createQuery("SELECT data FROM Unit data WHERE data.id IN (:ids)", Unit.class)
                .setParameter("ids", Arrays.asList(ids))
                .getResultList();
    }
}
