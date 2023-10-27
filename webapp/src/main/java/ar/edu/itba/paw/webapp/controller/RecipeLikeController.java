package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.servicesInterface.RecipeLikeService;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecipeLikeController {
    private RecipeLikeService recipeLikeService;

    @Autowired
    private BaseUserTemplateController baseUserTemplateController;

    @RequestMapping(value = "/rate/like/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView likeRecipe(@PathVariable final long recipeId) {
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        recipeLikeService.makeRecipeLiked(userId, recipeId);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/recipeDetail/" + recipeId);

        return mav;
    }

    @RequestMapping(value = "/rate/dislike/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView dislikeRecipe(@PathVariable final long recipeId) {
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        recipeLikeService.makeRecipeDisliked(userId, recipeId);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/recipeDetail/" + recipeId);
        return mav;
    }

    @RequestMapping(value = "/rate/remove/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView unrateRecipe(@PathVariable final long recipeId) {
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        recipeLikeService.removeRecipeRating(userId, recipeId);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/recipeDetail/" + recipeId);
        return mav;
    }

    @RequestMapping(value = "/feed/rate/like/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView likeRecipeFromFeed(@PathVariable final long recipeId, @RequestParam final long pageNumber) {
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        recipeLikeService.makeRecipeLiked(userId, recipeId);
        if(pageNumber > 1){
           return new ModelAndView("redirect:/following?page=" + pageNumber);
        }
        return new ModelAndView("redirect:/following");
    }

    @RequestMapping(value = "/feed/rate/dislike/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView dislikeRecipeFromFeed(@PathVariable final long recipeId, @RequestParam final long pageNumber) {
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        recipeLikeService.makeRecipeDisliked(userId, recipeId);
        if(pageNumber > 1){
            return new ModelAndView("redirect:/following?page=" + pageNumber);
        }
        return new ModelAndView("redirect:/following");
    }

    @RequestMapping(value = "/feed/rate/remove/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView unrateRecipeFromFeed(@PathVariable final long recipeId, @RequestParam final long pageNumber) {
        long userId = baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new);
        recipeLikeService.removeRecipeRating(userId, recipeId);
        if(pageNumber > 1){
            return new ModelAndView("redirect:/following?page=" + pageNumber);
        }
        return new ModelAndView("redirect:/following");
    }


    @Autowired
    public void setLikesService(RecipeLikeService likedRecipesService) {
        this.recipeLikeService = likedRecipesService;
    }

}
