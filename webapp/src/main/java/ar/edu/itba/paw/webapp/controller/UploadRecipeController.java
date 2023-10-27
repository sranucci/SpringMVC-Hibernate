package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.enums.AvailableDifficulties;
import ar.edu.itba.paw.models.category.Category;
import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.unit.Unit;
import ar.edu.itba.paw.servicesInterface.category.CategoryService;
import ar.edu.itba.paw.servicesInterface.ingredient.IngredientsService;
import ar.edu.itba.paw.servicesInterface.unit.UnitsService;
import ar.edu.itba.paw.webapp.dtos.RecipeFormDto;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.webapp.exceptions.UserDoesNotOwnRecipe;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.forms.RecipeForm;
import ar.edu.itba.paw.webapp.forms.RecipeFormEdition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
public class UploadRecipeController {
    private RecipeService recipeService;
    private IngredientsService ingredientsService;
    private CategoryService categoryService;
    private UnitsService unitsService;


    private static final Logger LOGGER = LoggerFactory.getLogger(UploadRecipeController.class);
    @Autowired
    private BaseUserTemplateController baseUserTemplateController;

    @RequestMapping(value = "/upload-recipe", method = RequestMethod.POST)
    public ModelAndView receiveRecipe(@Valid @ModelAttribute("recipeForm") final RecipeForm recipeForm, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return recipeForm(recipeForm);
        }
        final long recipeId = recipeService.create(recipeForm.asUploadedFormDto(), baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new));
        LOGGER.info("user with id {} created recipe with id {}", baseUserTemplateController.getCurrentUserId().get(),recipeId);
        return new ModelAndView("redirect:/myRecipes");
    }


    @RequestMapping(value = "/upload-recipe", method = RequestMethod.GET)
    public ModelAndView recipeForm(@ModelAttribute("recipeForm") final RecipeForm recipeForm) {
        ModelAndView mav = new ModelAndView("/recipeForm");
        mav.addObject(getFormData());
        return mav;
    }



    @RequestMapping(value = "editRecipe/{recipe_id:\\d+}", method = RequestMethod.GET)
    public ModelAndView editRecipe(@ModelAttribute("recipeForm")final RecipeFormEdition editRecipe, @PathVariable("recipe_id") final long recipeId){
        Optional<Recipe> maybeRecipe = recipeService.recoverUserRecipe(recipeId, baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new));
        if ( !maybeRecipe.isPresent()){
            LOGGER.warn("User with id {} tried to get an unexisting recipe", baseUserTemplateController.getCurrentUserId().get());
            throw new UserDoesNotOwnRecipe();
        }
        editRecipe.setFromRecipe(maybeRecipe.get());
        return obtainEditionView(recipeId);
    }


    private ModelAndView obtainEditionView(long recipeId) {
        ModelAndView mav = new ModelAndView("/recipeForm");
        mav.addObject(getFormData());
        mav.addObject("recipeEdit", true);
        mav.addObject("recipeId", recipeId);
        return mav;
    }


    @RequestMapping(value = "editRecipe/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView editRecipe(@Valid @ModelAttribute("recipeForm") final RecipeFormEdition editRecipe, final BindingResult bindingResult, @PathVariable("recipeId") final long recipeId) {
        if (bindingResult.hasErrors()) {
            return obtainEditionView(recipeId);
        }
        final boolean successUpdate = recipeService.updateRecipe(editRecipe.asUploadedFormDto(), baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), recipeId);
        if ( !successUpdate )
            LOGGER.warn("user with id {} tried to comment a not owned or inexisting recipe with id {}", baseUserTemplateController.getCurrentUserId().get(),recipeId);
        else
            LOGGER.info("user with id {} edited recipe with id {}", baseUserTemplateController.getCurrentUserId().get(), recipeId);
        return new ModelAndView("redirect:/myRecipes");
    }


    private RecipeFormDto getFormData() {
        List<Ingredient> ingredients = ingredientsService.getAllIngredients();
        List<Category> categories = categoryService.getAllCategories();
        List<Unit> units = unitsService.getAllUnits();
        AvailableDifficulties[] difficulties = AvailableDifficulties.values();
        return new RecipeFormDto(ingredients, units, categories, difficulties);
    }


    @Autowired
    public void setIngredientService(final IngredientsService ingredientsService){
        this.ingredientsService = ingredientsService;
    }

    @Autowired
    public void setCategoryService(final CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUnitsService(final UnitsService unitsService){
        this.unitsService = unitsService;
    }

    @Autowired
    public void setRecipeService(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }


}
