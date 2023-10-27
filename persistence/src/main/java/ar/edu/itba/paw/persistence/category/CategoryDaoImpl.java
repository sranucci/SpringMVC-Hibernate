package ar.edu.itba.paw.persistence.category;

import ar.edu.itba.paw.models.category.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
public class CategoryDaoImpl implements ar.edu.itba.persistenceInterface.CategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Category> getAllCategories() {
        return em.createQuery("SELECT c FROM Category c", Category.class)
                .getResultList();
    }

    @Override
    public List<Category> getAllCategoriesForId(Long[] ids){
       return em.createQuery("SELECT cat FROM Category cat WHERE cat.categoryId IN :ids", Category.class)
                .setParameter("ids", Arrays.asList(ids)).getResultList();
    }
}
