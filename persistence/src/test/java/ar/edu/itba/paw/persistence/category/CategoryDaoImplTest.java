package ar.edu.itba.paw.persistence.category;
import ar.edu.itba.paw.models.category.Category;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.CategoryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;
import static org.junit.Assert.*;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateCategoryDaoImplTest.sql"})
public class CategoryDaoImplTest {


    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testGetAllCategories(){
        List<Category> categoryList = categoryDao.getAllCategories();
        assertEquals(3, categoryList.size());
        assertEquals(CATEGORY_STR_1, categoryList.get(0).getName());
        assertEquals(Long.valueOf(CATEGORY_ID_1), categoryList.get(0).getCategoryId());
        assertEquals(CATEGORY_STR_2, categoryList.get(1).getName());
        assertEquals(Long.valueOf(CATEGORY_ID_2), categoryList.get(1).getCategoryId());
    }

    @Test
    public void testGetAllCategoriesForID(){
        List<Category> categoryList = categoryDao.getAllCategoriesForId(new Long[]{CATEGORY_ID_1, CATEGORY_ID_2});
        assertEquals(2, categoryList.size());
        assertEquals(CATEGORY_STR_1, categoryList.get(0).getName());
        assertEquals(Long.valueOf(CATEGORY_ID_1), categoryList.get(0).getCategoryId());
        assertEquals(CATEGORY_STR_2, categoryList.get(1).getName());
        assertEquals(Long.valueOf(CATEGORY_ID_2), categoryList.get(1).getCategoryId());
    }

}



