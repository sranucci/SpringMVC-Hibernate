package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.servicesInterface.RecipeLikeService;
import ar.edu.itba.paw.servicesInterface.MailService;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.servicesInterface.exceptions.RecipeNotFoundException;
import ar.edu.itba.persistenceInterface.RecipeLikeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecipeLikeServiceImpl implements RecipeLikeService {
    private final RecipeLikeDao recipeLikeDao;
    private final RecipeService recipeService;
    private final MailService mailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeLikeServiceImpl.class);

    @Autowired
    public RecipeLikeServiceImpl(final RecipeLikeDao recipeLikeDao, final RecipeService recipeService, final MailService mailService) {
        this.recipeLikeDao = recipeLikeDao;
        this.recipeService = recipeService;
        this.mailService = mailService;
    }


    @Transactional(readOnly = true)
    @Override
    public Boolean isRecipeLikedById(long userId, long recipeId) {
        return recipeLikeDao.isRecipeLikedById(userId, recipeId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean isRecipeDislikedById(long userId, long recipeId) {
        return recipeLikeDao.isRecipeDislikedById(userId, recipeId);
    }


    @Transactional
    @Override
    public void makeRecipeLiked(long userId, long recipeId) {
        Recipe recipe = recipeService.getRecipe(recipeId).orElseThrow(RecipeNotFoundException::new);
        recipeLikeDao.makeRecipeLiked(userId, recipeId);

        if (recipe.getLikes() > 0 && recipe.getLikes() % 5 == 0) { //cuando el usuario llega a un numero de likes multiplo de 5 se envia un mail felicitando
            User recipeOwner = recipe.getUser();
            mailService.sendNLikesEmail(recipeOwner.getEmail(), recipe.getTitle(), recipeOwner.getName(), recipe.getLikes(), recipeId, recipeOwner.getLocale());
        }

    }

    @Transactional
    @Override
    public void makeRecipeDisliked(long userId, long recipeId) {
        recipeService.getRecipe(recipeId).orElseThrow(RecipeNotFoundException::new);
        recipeLikeDao.makeRecipeDisliked(userId, recipeId);
    }

    @Transactional
    @Override
    public void removeRecipeRating(long userId, long recipeId) {
        recipeService.getRecipe(recipeId).orElseThrow(RecipeNotFoundException::new);
        recipeLikeDao.removeRecipeRating(userId, recipeId);
    }
}
