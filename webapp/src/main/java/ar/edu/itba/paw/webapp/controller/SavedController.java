package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.servicesInterface.SavedRecipesService;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SavedController {
    private SavedRecipesService savedRecipesService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SavedController.class);
    @Autowired
    private BaseUserTemplateController baseUserTemplateController;


    @RequestMapping(value = "/save/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView saveRecipe(@PathVariable final long recipeId) {
        savedRecipesService.saveRecipe(baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), recipeId);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/recipeDetail/" + recipeId);
        LOGGER.info("user with id {} saved recipe {} ", baseUserTemplateController.getCurrentUserId().get(),recipeId);
        return mav;
    }

    @RequestMapping(value = "/unsave/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView unSaveRecipe(@PathVariable final long recipeId) {
        savedRecipesService.deleteSavedRecipe((baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new)), recipeId);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/recipeDetail/" + recipeId);
        LOGGER.info("user with id {} unsaved the recipe {}",baseUserTemplateController.getCurrentUserId().get(), recipeId);
        return mav;
    }

    @RequestMapping(value = "/feed/save/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView saveRecipeFromFeed(@PathVariable final long recipeId, @RequestParam final long pageNumber) {
        savedRecipesService.saveRecipe(baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), recipeId);
        LOGGER.info("user with id {} saved recipe {} ", baseUserTemplateController.getCurrentUserId().get(),recipeId);
        if(pageNumber > 1){
            return new ModelAndView("redirect:/following?page=" + pageNumber);
        }
        return new ModelAndView("redirect:/following");
    }

    @RequestMapping(value = "/feed/unsave/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView unSaveRecipeFromFeed(@PathVariable final long recipeId, @RequestParam final long pageNumber) {
        savedRecipesService.deleteSavedRecipe((baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new)), recipeId);
        LOGGER.info("user with id {} unsaved the recipe {}",baseUserTemplateController.getCurrentUserId().get(), recipeId);
        if(pageNumber > 1){
            return new ModelAndView("redirect:/following?page=" + pageNumber);
        }
        return new ModelAndView("redirect:/following");
    }

    @Autowired
    public void setSrs(SavedRecipesService savedRecipesService) {
        this.savedRecipesService = savedRecipesService;
    }
}
