package ar.edu.itba.paw.services;

import ar.edu.itba.paw.dtos.UploadedRecipeFormDto;
import ar.edu.itba.paw.models.category.Category;
import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.unit.Unit;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.RecipeImageService;
import ar.edu.itba.paw.servicesInterface.category.CategoryService;
import ar.edu.itba.paw.servicesInterface.ingredient.IngredientsService;
import ar.edu.itba.paw.servicesInterface.unit.UnitsService;
import ar.edu.itba.persistenceInterface.RecipeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    private static final long USER_ID = 1;


    private static final long RECIPE_ID = 1;
    protected static final String RECIPE_TITLE = "title";
    protected static final String DESCRIPTION = "description";
    protected static final boolean IS_PRIVATE = true;
    protected static final int TOTAL_MINUTES = 75;

    protected static final String LANGUAGE = "en";
    protected static final Long DIFFICULTY = 1L;
    protected static final int SERVINGS = 4;
    protected static final String[] INSTRUCTIONS_ARRAY = {"Instruction 1", "Instruction 2", "Ins3"};
    protected static final byte[] IMG_BYTEA= new byte[]{0x00};
    protected static final Long[] CATEGORIES= new Long[]{1L};
    protected static final Float[] QUANTITIES= new Float[]{1F};
    protected static final String[] INGREDIENTS= new String[]{"salt"};
    protected static final Long[] MEASURE_IDS= new Long[]{1L};
    protected static final Long IMAGE_ID =  1L;



    @Mock
    private RecipeDao recipeDao;
    @Mock
    private IngredientsService ingredientsService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UnitsService unitsService;

    @Mock
    private RecipeImageService imageService;


    @InjectMocks
    private RecipeServiceImpl rs;

    @Test
    public void testCreate() {
        List<byte[]> recipeImages= new ArrayList();
        recipeImages.add(IMG_BYTEA);

        List<Long> imagesIds = new ArrayList<>();
        imagesIds.add(IMAGE_ID);

        UploadedRecipeFormDto uploadedRecipeFormDto = new UploadedRecipeFormDto(LANGUAGE,
                SERVINGS, CATEGORIES, RECIPE_TITLE, recipeImages.get(0), recipeImages, DESCRIPTION,
                QUANTITIES, INGREDIENTS, MEASURE_IDS, INSTRUCTIONS_ARRAY, TOTAL_MINUTES,
                DIFFICULTY, IS_PRIVATE, imagesIds);

        Recipe recipe = new Recipe();
        recipe.setRecipeId(RECIPE_ID);
        recipe.setLanguage(LANGUAGE);
        recipe.setTitle(RECIPE_TITLE);
        recipe.setDescription(DESCRIPTION);
        recipe.setUser(new User(USER_ID, "mail", "pass", "name", "lastname", true, true, Locale.ENGLISH ));
        recipe.setPrivate(IS_PRIVATE);
        recipe.setTotalMinutes(TOTAL_MINUTES);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setServings(SERVINGS);
        recipe.setInstructions("Instruction 1#Instruction 2#Ins3");

        when(recipeDao.create(LANGUAGE, RECIPE_TITLE,DESCRIPTION,USER_ID,
                IS_PRIVATE,TOTAL_MINUTES,DIFFICULTY,SERVINGS,INSTRUCTIONS_ARRAY))
                .thenReturn(recipe);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Breakfast&Brunch"));
        when(categoryService.getAllCategoriesForIds(CATEGORIES)).thenReturn(categories);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add( new Ingredient(1, "salt"));
        when(ingredientsService.getAllIngredientsByName(INGREDIENTS)).thenReturn(ingredients);


        List<Unit> units = new ArrayList<>();
        units.add( new Unit(1L, "g"));
        when(unitsService.getUnitsById(MEASURE_IDS)).thenReturn(units);


        assertEquals(1, rs.create(uploadedRecipeFormDto, USER_ID));

    }

    @Test
    public void testGetRecipe(){
        Recipe recipe = new Recipe();
        recipe.setRecipeId(RECIPE_ID);
        when(recipeDao.getRecipe(RECIPE_ID)).thenReturn(Optional.of(recipe));
        assertTrue(rs.getRecipe(RECIPE_ID).isPresent());
    }


}

