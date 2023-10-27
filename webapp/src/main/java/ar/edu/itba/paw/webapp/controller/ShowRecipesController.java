package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.dtos.BaseShowRecipeDataDto;
import ar.edu.itba.paw.enums.LanguagesForSort;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.webapp.dtos.FilterAutocompleteDataDto;
import ar.edu.itba.paw.dtos.RecipesAndSize;
import ar.edu.itba.paw.dtos.ShowRecipesDto;
import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.ShowRecipePages;
import ar.edu.itba.paw.enums.SortOptions;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.servicesInterface.category.CategoryService;
import ar.edu.itba.paw.servicesInterface.ingredient.IngredientsService;
import ar.edu.itba.paw.webapp.exceptions.InvalidArgumentException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;


//Controller handles recipe request once a particular recipe is clicked
@Controller
public class ShowRecipesController {

    private final int PAGESIZE = 9;
    private RecipeService recipeService;
    private CategoryService categoryService;
    private IngredientsService ingredientsService;
    private UserService userService;

    @Autowired
    private BaseUserTemplateController baseUserTemplateController;

    private ModelAndView showRecipes(
            String pageHeadTitle,
            String pageTitle,
            ShowRecipePages showRecipePage,
            Optional<AvailableDifficultiesForSort> difficulty,
            Optional<SortOptions> sort,
            List<Integer> selectedCategories,
            Optional<String> selectedIngredients,
            Optional<String> searchBarQuery,
            Optional<Long> page,
            Optional<LanguagesForSort> language

    ) {
        if (page.isPresent() && page.get() < 1)
            throw new InvalidArgumentException();

        RecipesAndSize recipesAndSize = recipeService.getRecipesByFilter(
                difficulty, selectedIngredients, selectedCategories,
                sort, searchBarQuery, showRecipePage, baseUserTemplateController.getCurrentUserId(), page, Optional.of(PAGESIZE),language
        );
        ModelAndView showRecipes = new ModelAndView("/showRecipes");

        ShowRecipesDto dto = new ShowRecipesDto(
                pageHeadTitle,
                pageTitle,
                recipesAndSize.getRecipeList(),
                page.orElse(1L),
                PAGESIZE,
                recipesAndSize.getTotalRecipes(),
                difficulty.orElse(AvailableDifficultiesForSort.ALL),
                sort.orElse(SortOptions.NONE),
                selectedCategories.stream().map(Object::toString).collect(Collectors.toList()), //el checkbox de materialize necesita strings
                selectedIngredients.orElse(""),
                searchBarQuery.orElse(""),
                language.orElse(LanguagesForSort.ALL)
        );

        showRecipes.addObject("dto", dto);
        showRecipes.addObject("autocompleteDto", getFilterAutocompleteData());
        return showRecipes;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView discover(
            @RequestParam(value = "difficulty") Optional<AvailableDifficultiesForSort> difficulty,
            @RequestParam(value = "sort") Optional<SortOptions> sort,
            @RequestParam(value = "selectedIngredients") Optional<String> selectedIngredients,
            @RequestParam(value = "selectedCategories", required = false, defaultValue = "") List<Integer> selectedCategories,
            @RequestParam(value = "searchBarQuery") Optional<String> searchBarQuery,
            @RequestParam(value = "page") Optional<Long> page,
            @RequestParam(value = "language")  Optional<LanguagesForSort> language

    ) {
        return showRecipes(
                "Discover",
                "Discover",
                ShowRecipePages.DISCOVER,
                difficulty,
                sort,
                selectedCategories,
                selectedIngredients,
                searchBarQuery,
                page,
                language
        );
    }

    @RequestMapping(value = "/myRecipes", method = RequestMethod.GET)
    public ModelAndView myRecipes(
            @RequestParam(value = "difficulty") Optional<AvailableDifficultiesForSort> difficulty,
            @RequestParam(value = "sort") Optional<SortOptions> sort,
            @RequestParam(value = "selectedIngredients") Optional<String> selectedIngredients,
            @RequestParam(value = "selectedCategories", required = false, defaultValue = "") List<Integer> selectedCategories,
            @RequestParam(value = "searchBarQuery") Optional<String> searchBarQuery,
            @RequestParam(value = "page") Optional<Long> page,
            @RequestParam(value = "language") Optional<LanguagesForSort> language


    ) {
        return showRecipes(
                "My Recipes",
                "MyRecipes",
                ShowRecipePages.MY_RECIPES,
                difficulty,
                sort,
                selectedCategories,
                selectedIngredients,
                searchBarQuery,
                page,
                language
        );
    }

    @RequestMapping(value = "/saved", method = RequestMethod.GET)
    public ModelAndView saved(
            @RequestParam(value = "difficulty") Optional<AvailableDifficultiesForSort> difficulty,
            @RequestParam(value = "sort") Optional<SortOptions> sort,
            @RequestParam(value = "selectedIngredients") Optional<String> selectedIngredients,
            @RequestParam(value = "selectedCategories", required = false, defaultValue = "") List<Integer> selectedCategories,
            @RequestParam(value = "searchBarQuery") Optional<String> searchBarQuery,
            @RequestParam(value = "page") Optional<Long> page,
            @RequestParam(value = "language") Optional<LanguagesForSort> language

    ) {
        return showRecipes(
                "Saved",
                "Saved",
                ShowRecipePages.SAVED,
                difficulty,
                sort,
                selectedCategories,
                selectedIngredients,
                searchBarQuery,
                page,
                language
        );
    }

    @RequestMapping(value = "/following", method = RequestMethod.GET)
    public ModelAndView feed(@RequestParam(value = "page") Optional<Integer> page) {
        if (page.isPresent() && page.get() < 1)
            throw new InvalidArgumentException();
        ModelAndView mav = new ModelAndView("/feed");
        RecipesAndSize rl = recipeService.getRecipesForFeed(baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), page, Optional.of(PAGESIZE));
        BaseShowRecipeDataDto dto = new BaseShowRecipeDataDto(
                "Following",
                "Following",
                rl.getRecipeList(),
                page.orElse(1),
                PAGESIZE,
                rl.getTotalRecipes()
        );
        User user = userService.findById(baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new)).orElseThrow(UserNotFoundException::new);
        mav.addObject("dto", dto);
        mav.addObject("usersToFollowSuggestion",userService.getUserSuggestionList());
        mav.addObject("recipesLiked", recipeService.getRecipesLiked(baseUserTemplateController.getCurrentUserId()));
        mav.addObject("recipesDisliked", recipeService.getRecipesDisliked(baseUserTemplateController.getCurrentUserId()));
        mav.addObject("recipesSaved",user.getSavedRecipes());
        return mav;
    }


    private FilterAutocompleteDataDto getFilterAutocompleteData() {
        Map<String, Object> autoCompleteMap = new HashMap<>();
        Map<Long, String> categoryMap = new HashMap<>();

        ingredientsService.getAllIngredients().forEach(ingredient -> autoCompleteMap.put(ingredient.getName(), null));
        categoryService.getAllCategories().forEach(category -> categoryMap.put(category.getCategoryId(), category.getName()));
        return new FilterAutocompleteDataDto(autoCompleteMap, categoryMap);
    }


    @RequestMapping("/upload-success")
    public ModelAndView getUploadSuccessScreen() {
        return new ModelAndView("/successScreen");
    }

    @Autowired
    public void setRecipeService(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Autowired
    public void setCategoryService(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setIngredientsService(final IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
