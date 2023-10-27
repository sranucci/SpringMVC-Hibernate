package ar.edu.itba.paw.services;

import ar.edu.itba.paw.dtos.RecipesAndSize;
import ar.edu.itba.paw.dtos.TripleIngredientSelectionDto;
import ar.edu.itba.paw.dtos.UploadedRecipeFormDto;
import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.LanguagesForSort;
import ar.edu.itba.paw.enums.ShowRecipePages;
import ar.edu.itba.paw.enums.SortOptions;
import ar.edu.itba.paw.models.category.Category;
import ar.edu.itba.paw.models.category.RecipeCategory;
import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.models.ingredient.RecipeIngredient;
import ar.edu.itba.paw.models.recipe.*;
import ar.edu.itba.paw.models.unit.Unit;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.RecipeImageService;
import ar.edu.itba.paw.servicesInterface.MailService;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.servicesInterface.category.CategoryService;
import ar.edu.itba.paw.servicesInterface.exceptions.RecipeNotFoundException;
import ar.edu.itba.paw.servicesInterface.ingredient.IngredientsService;
import ar.edu.itba.paw.servicesInterface.unit.UnitsService;
import ar.edu.itba.persistenceInterface.RecipeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeDao recipeDao;
    private final IngredientsService ingredientsService;
    private final CategoryService categoryService;
    private final MailService mailService;
    private final RecipeImageService recipeImageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeServiceImpl.class);
    private final UnitsService unitsService;
    private final UserService userService;

    @Autowired
    public RecipeServiceImpl(final RecipeDao recipeDao, final IngredientsService ingredientsService,
                             final CategoryService categoryService, final MailService mailService, final RecipeImageService recipeImageService, final UnitsService unitsService, final UserService userService) {
        this.recipeDao = recipeDao;
        this.recipeImageService = recipeImageService;
        this.ingredientsService = ingredientsService;
        this.categoryService = categoryService;
        this.mailService = mailService;
        this.unitsService = unitsService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void removeRecipeNotifyingUser(long recipeId, String deletionMotive) throws RecipeNotFoundException {
        Recipe recipe = recipeDao.getRecipe(recipeId).orElseThrow(RecipeNotFoundException::new);

        recipeDao.removeRecipe(recipe);

        User recipeOwner = recipe.getUser();
        mailService.sendRecipeDeletionEmail(recipeOwner.getEmail(), recipe.getTitle(), deletionMotive, recipeOwner.getLocale());
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Recipe> recoverUserRecipe(long recipeId, long userId) {
        Optional<Recipe> maybeRecipe = recipeDao.getRecipe(recipeId);
        if (!maybeRecipe.isPresent() || userId != maybeRecipe.get().getUser().getId())
            return Optional.empty();
        return maybeRecipe;

    }
    @Transactional
    @Override
    public boolean removeRecipe(long recipeId, long userId) {
        return recipeDao.removeRecipe(recipeId, userId);
    }


    @Transactional
    @Override
    public long create(UploadedRecipeFormDto rf, long userId) {
        Recipe recipe = recipeDao.create(rf.getLanguage(),rf.getTitle(), rf.getDescription(), userId, rf.isPrivate(), rf.getTotalTime(), rf.getDifficultyId(), rf.getServings(), rf.getInstructions());
        List<RecipeImage> imageList = adaptRecipeImagesWithMainImage(recipe,rf);
        recipe.setRecipeImages(imageList);
        List<RecipeCategory> categoryList = adaptRecipeCategories(recipe,rf);
        recipe.setRecipeCategories(categoryList);
        List<RecipeIngredient> ingredientList = adaptRecipeIngredients(recipe,rf);
        recipe.setIngredients(ingredientList);
        if(!rf.isPrivate()){
            User user = recipe.getUser();
            List<User> followers = userService.getFollowers(userId);
            for (User us: followers) {
                mailService.sendNewRecipeToFollowers(us.getEmail(),user.getName()+" "+user.getLastname(),us.getName()+" "+us.getLastname(),recipe.getRecipeId(), recipe.getTitle(), us.getLocale());
            }
        }
        return recipe.getRecipeId();
    }


    private List<RecipeIngredient> adaptRecipeIngredients(Recipe recipe, UploadedRecipeFormDto rf){
        Map<String, Ingredient> ingMap = new HashMap<>();
        ingredientsService.getAllIngredientsByName(rf.getIngredients()).forEach(ingredient -> ingMap.put(ingredient.getName(),ingredient));
        Map<Long, Unit> unitMap = new HashMap<>();
        unitsService.getUnitsById(rf.getMeasureIds()).forEach(unit -> unitMap.put(unit.getId(),unit));
        List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
        for ( TripleIngredientSelectionDto recipeIng : rf.getIngredientsIterable()){
            Ingredient ingr = ingMap.get(recipeIng.getIngredient());
            Unit unit = unitMap.get(recipeIng.getUnitId());
            recipeIngredientList.add(new RecipeIngredient(recipe,ingr, unit, recipeIng.getQuantity()));
        }
        return recipeIngredientList;
    }

    private List<RecipeCategory> adaptRecipeCategories(Recipe recipe, UploadedRecipeFormDto rf){
        List<Category> categories = categoryService.getAllCategoriesForIds(rf.getCategories());
        return categories.stream().map(categ -> new RecipeCategory(recipe,categ)).collect(Collectors.toList());
    }

    private List<RecipeImage> adaptRecipeImages(Recipe recipe, UploadedRecipeFormDto rf){
        List<RecipeImage> imgList = new ArrayList<>();
        for (int i = 0; i < rf.getRecipeImages().size(); i++) {
            imgList.add(new RecipeImage(recipe, rf.getRecipeImages().get(i), false));
        }
        return imgList;
    }

    private List<RecipeImage> adaptRecipeImagesWithMainImage(Recipe recipe, UploadedRecipeFormDto rf){
        List<RecipeImage> imageList = adaptRecipeImages(recipe,rf);
        imageList.add(0, new RecipeImage(recipe,rf.getMainImage(),true));
        return imageList;
    }



    @Transactional
    @Override
    public boolean updateRecipe(UploadedRecipeFormDto rf, long userId, long recipeId) {
        Optional<Recipe> maybeRecipe = recipeDao.getRecipe(recipeId);
        if (!maybeRecipe.isPresent() || maybeRecipe.get().getUser().getId() != userId)
            return false;


        Recipe recipe = maybeRecipe.get();

        List<RecipeIngredient> ingredients = adaptRecipeIngredients(recipe, rf);
        List<RecipeCategory> categories = adaptRecipeCategories(recipe, rf);
        List<RecipeImage> images = null;
        if ( rf.getRecipeImages() != null && !rf.getRecipeImages().isEmpty() ){
            images = adaptRecipeImages(recipe,rf);
        }

        RecipeImage mainImage = null;
        if ( rf.getMainImage() != null) {
            mainImage = new RecipeImage(recipe,rf.getMainImage(),true);
        }

        recipeDao.updateRecipe(
                rf.getLanguage(),
                rf.getTitle(),
                rf.getDescription(),
                rf.isPrivate(),
                rf.getTotalTime(),
                rf.getDifficultyId(),
                rf.getServings(),
                rf.getInstructions(),
                ingredients,
                categories,
                images,
                mainImage,
                recipe
        );

        return true;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<RecipeImage> getImage(long imageId) {
        return recipeImageService.getImage(imageId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Recipe> getRecipe(long recipeId) {
        return recipeDao.getRecipe(recipeId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Recipe> getFullRecipe(long recipeId, Optional<Integer> commentsToBring) {
        return recipeDao.getRecipe(recipeId,commentsToBring);
    }


    @Transactional(readOnly = true)
    @Override
    public RecipesAndSize getRecipesByFilter(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients, List<Integer> categories, Optional<SortOptions> sort, Optional<String> query, ShowRecipePages pageToShow, Optional<Long> userId, Optional<Long> page, Optional<Integer> pageSize, Optional<LanguagesForSort> language) {
        List<Recipe> recipeList = recipeDao.getRecipesByFilter(difficulty, ingredients, categories, sort, query, pageToShow, userId, page, pageSize, language);
        long totalRecipes = recipeDao.getTotalNumberRecipesByFilterForPagination(difficulty, ingredients, categories, sort, query, pageToShow, userId, page, pageSize, language);
        return new RecipesAndSize(recipeList, totalRecipes);
    }

    @Transactional(readOnly = true)
    @Override
    public RecipesAndSize getRecipesForFeed(Long currentUserId, Optional<Integer> page, Optional<Integer> pageSize) {
        List<Recipe> recipeList = recipeDao.getRecipesForFeed(currentUserId, page, pageSize);
        long totalRecipes = recipeDao.getNumberOfRecipesForFeed(currentUserId);
        return new RecipesAndSize(recipeList, totalRecipes);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getRecipesLiked(Optional<Long> currentUserId) {
        return recipeDao.getRecipesLiked(currentUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> getRecipesDisliked(Optional<Long> currentUserId) {
        return recipeDao.getRecipesDisliked(currentUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public RecipesAndSize getRecipesForProfile(Long userId, Optional<Integer> page, Optional<Integer> pageSize) {
        List<Recipe> recipeList = recipeDao.getRecipesForProfile(userId, page, pageSize);
        long totalRecipes = recipeDao.getNumberOfRecipesForProfile(userId);
        return new RecipesAndSize(recipeList, totalRecipes);
    }
}
