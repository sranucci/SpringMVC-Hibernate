package ar.edu.itba.paw.servicesInterface.category;

import ar.edu.itba.paw.models.category.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    List<Category> getAllCategoriesForIds(Long[] ids);
}
