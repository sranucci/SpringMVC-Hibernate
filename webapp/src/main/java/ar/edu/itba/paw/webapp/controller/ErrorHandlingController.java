package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.servicesInterface.exceptions.*;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandlingController {

    @Autowired
    BaseUserTemplateController baseUserTemplateController;

    private ModelAndView callErrorPage(String title, String message, int errorCode) {
        ModelAndView mav = new ModelAndView("/errorPage");
        mav.addObject("title", title);
        mav.addObject("message", message);
        mav.addObject("errorCode", errorCode);
        mav.addObject("currentUser", baseUserTemplateController.getUserDto());
        return mav;
    }

    @ExceptionHandler(RecipeImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleRecipeImageNotFoundException(RecipeImageNotFoundException ex) {
        return callErrorPage("errorPage.recipeRecipeImageNotFound.title", "errorPage.recipeRecipeImageNotFound.message", 404);
    }

    @ExceptionHandler(UserImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleUserImageNotFoundException(UserImageNotFoundException ex) {
        return callErrorPage("errorPage.recipeUserImageNotFound.title", "errorPage.recipeUserImageNotFound.message", 404);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleUserNotFoundException(UserNotFoundException ex) {
        return callErrorPage("errorPage.userNotFound.title", "errorPage.userNotFound.message", 404);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleRecipeNotFoundException(RecipeNotFoundException ex) {
        return callErrorPage("errorPage.recipeNotFound.title", "errorPage.recipeNotFound.message", 404);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return callErrorPage("errorPage.categoryNotFound.title", "errorPage.categoryNotFound.message", 404);
    }

    @ExceptionHandler(DifficultyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleDifficultyNotFoundException(DifficultyNotFoundException ex) {
        return callErrorPage("errorPage.difficultyNotFound.title", "errorPage.difficultyNotFound.message", 404);
    }

    @ExceptionHandler(SortOptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleSortOptionNotFoundException(SortOptionNotFoundException ex) {
        return callErrorPage("errorPage.sortOptionNotFound.title", "errorPage.sortOptionNotFound.message", 404);
    }

    @ExceptionHandler(UserDoesNotOwnRecipe.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleUserDoesNotOwnRecipeException(UserDoesNotOwnRecipe ex) {
        return callErrorPage("errorPage.userDoesNotOwnRecipe.title", "errorPage.userDoesNotOwnRecipe.message", 404);
    }

    @ExceptionHandler({TypeMismatchException.class, InvalidArgumentException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView typeMismatch(Exception ex) {
        return callErrorPage("errorPage.typeMismatch.title", "errorPage.typeMismatch.message", 400);
    }

    @ExceptionHandler({ NoHandlerFoundException.class, CommentNotFoundForDeletionException.class })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView error404(Exception ex) {
        return callErrorPage("errorPage.404.title", "errorPage.404.message", 404);
    }


    @ExceptionHandler(FailedToLoadResourceException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView resourceNotFound(Exception ex){
        return callErrorPage("errorPage.resourceNoFound.title", "errorPage.resourceNoFound.message", 500);
    }



    @ExceptionHandler(Exception.class)
   @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerError(Exception ex) {
        return callErrorPage("errorPage.internalServerError.title", "errorPage.internalServerError.message", 500);
    }



}
