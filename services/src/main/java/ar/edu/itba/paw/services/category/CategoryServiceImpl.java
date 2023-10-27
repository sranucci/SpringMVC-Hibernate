package ar.edu.itba.paw.services.category;

import ar.edu.itba.paw.models.category.Category;
import ar.edu.itba.paw.servicesInterface.category.CategoryService;
import ar.edu.itba.persistenceInterface.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(final CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }


    @Transactional
    @Override
    public List<Category> getAllCategoriesForIds(Long[] ids){
        return categoryDao.getAllCategoriesForId(ids);
    }

}
