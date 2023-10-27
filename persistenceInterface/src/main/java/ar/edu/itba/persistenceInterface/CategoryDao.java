package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.category.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getAllCategories();

    List<Category> getAllCategoriesForId(Long[] ids);

}
