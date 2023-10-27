package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.servicesInterface.RecipeLikeService;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.servicesInterface.SavedRecipesService;
import ar.edu.itba.paw.servicesInterface.UserService;
import ar.edu.itba.paw.servicesInterface.comments.CommentsLikesService;
import ar.edu.itba.paw.servicesInterface.comments.CommentsService;
import ar.edu.itba.paw.servicesInterface.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.servicesInterface.exceptions.RecipeNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.CommentNotFoundForDeletionException;
import ar.edu.itba.paw.webapp.exceptions.InvalidArgumentException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.forms.CommentForm;
import ar.edu.itba.paw.webapp.forms.DeleteCommentForm;
import ar.edu.itba.paw.webapp.forms.DeletionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RecipeDetailController {
    private RecipeService recipeService;
    private RecipeLikeService recipeLikeService;
    private SavedRecipesService savedRecipesService;
    private CommentsService commentsService;
    private CommentsLikesService commentsLikesService;

    private UserService userService;

    @Autowired
    private BaseUserTemplateController baseUserTemplateController;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDetailController.class);

    @RequestMapping("/recipeDetail/{recipeId:\\d+}")
    public ModelAndView recipeDetail(
            @PathVariable("recipeId") final long recipeId,
            @RequestParam("commentsToBring") Optional<Integer> commentsToBring,
            @ModelAttribute("commentForm") CommentForm commentForm,
            @ModelAttribute("deleteForm") DeletionForm recipeDeletionForm,
            @ModelAttribute("deleteCommentForm") DeleteCommentForm deletionCommentForm,
            @RequestParam(value = "commented", required = false) Optional<Boolean> commented
    ) {
        if (commentsToBring.isPresent() && commentsToBring.get() < 0)
            throw new InvalidArgumentException();
        final ModelAndView mav = new ModelAndView("/recipeDetail");
        Recipe fullRecipe = recipeService.getFullRecipe(recipeId, commentsToBring).orElseThrow(RecipeNotFoundException::new);

        mav.addObject("recipe", fullRecipe);
        if (baseUserTemplateController.getCurrentUserId().isPresent()) {
            long userId = baseUserTemplateController.getCurrentUserId().get();
            mav.addObject("isSaved", savedRecipesService.isRecipeSavedByUser(userId, recipeId));
            mav.addObject("isLiked", recipeLikeService.isRecipeLikedById(userId, recipeId));
            mav.addObject("isDisliked", recipeLikeService.isRecipeDislikedById(userId, recipeId));
            boolean isFollowed = userService.getFollows(userId, fullRecipe.getUser().getId());
            mav.addObject("isFollowed",isFollowed);
            if(commented.isPresent())
                mav.addObject("commented", commented.get());
            else
                mav.addObject("commented", false);
        } else {
            mav.addObject("isSaved", false);
            mav.addObject("isLiked", false);
            mav.addObject("isDisliked", false);
            mav.addObject("commented", false);
        }
        mav.addObject("recipeUser", fullRecipe.getUser());
        return mav;
    }


    @RequestMapping(value = "/commentRecipe/{recipeId:\\d+}", method = RequestMethod.POST)
    public ModelAndView comment(@Valid @ModelAttribute("commentForm") final CommentForm commentForm, final BindingResult errors,
                                @PathVariable("recipeId") final long recipeId,
                                @ModelAttribute("deleteForm") DeletionForm recipeDeletionForm,
                                @ModelAttribute("deleteCommentForm") DeleteCommentForm deleteCommentForm) {
        if (errors.hasErrors()) {
            return recipeDetail(recipeId, Optional.empty(), commentForm, recipeDeletionForm, deleteCommentForm, Optional.of(false));
        }
        Comment c = commentsService.createComment(recipeId, baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), commentForm.getComment());
        LOGGER.info("user with id {} created a comment with id {} on recipe with id {}", baseUserTemplateController.getCurrentUserId().get(),c.getCommentId(),recipeId);
        final ModelAndView mav = new ModelAndView("redirect:/ recipeDetail" + "/" + recipeId);
        mav.addObject("commented", true);
        return mav;

    }

    @RequestMapping(value = "/recipeDetail/likeComment/{commentId:\\d+}", method = RequestMethod.POST)
    public ModelAndView likeComment(@PathVariable("commentId") final long commentId) {
        //get is secure, user is logged in
        return likeStatusHelper(commentId, baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), true);
    }


    private ModelAndView likeStatusHelper(final long commentId, final long userId, final boolean likeStatus) {
        Comment c = commentsLikesService.setCommentLikeStatus(commentId, userId, likeStatus);
        return new ModelAndView("redirect:/recipeDetail/" + c.getRecipe().getRecipeId());
    }


    @RequestMapping(value = "/recipeDetail/dislikeComment/{commentId:\\d+}", method = RequestMethod.POST)
    public ModelAndView dislikeComment(@PathVariable("commentId") final long commentId) {
        return likeStatusHelper(commentId, baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new), false);
    }

    @RequestMapping(value = "/deleteRecipeComment/{recipeId:\\d+}/{commentId:\\d+}", method = RequestMethod.POST)
    public ModelAndView deleteRecipeComment(@Valid @ModelAttribute("deleteCommentForm") DeleteCommentForm form, final BindingResult bindingResult, @PathVariable("recipeId") long recipeId, @PathVariable("commentId") long commentId) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/recipeDetail/" + recipeId + "?commentError=" + commentId);
        }
        try {
            commentsService.removeCommentNotifyingUser( commentId, form.getDeletionMotive());
        } catch ( CommentNotFoundException e){
            LOGGER.error("Admin with id {} tried to delete an un-existing comment",baseUserTemplateController.getCurrentUserId().orElseThrow(UserNotLoggedInException::new));
            throw new CommentNotFoundForDeletionException();
        }
        return new ModelAndView("redirect:/recipeDetail/" + recipeId);
    }

    @Autowired
    public void setCommentLikeService(CommentsLikesService commentsLikesService) {
        this.commentsLikesService = commentsLikesService;
    }

    @Autowired
    public void setCommentsService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @Autowired
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Autowired
    public void setLikesService(RecipeLikeService recipeLikeService) {this.recipeLikeService = recipeLikeService;}

    @Autowired
    public void setSavedRecipesService(SavedRecipesService savedRecipesService) {
        this.savedRecipesService = savedRecipesService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
