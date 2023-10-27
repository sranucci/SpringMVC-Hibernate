package ar.edu.itba.paw.persistence.user;

import ar.edu.itba.paw.models.RecipeLikeDislike;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository

public class RecipeLikeDaoImpl implements ar.edu.itba.persistenceInterface.RecipeLikeDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean isRecipeLikedById(long userId, long recipeId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(ld) FROM RecipeLikeDislike ld WHERE ld.user.id = :userId AND ld.recipe.recipeId = :recipeId AND ld.isLike = true",
                Long.class);
        query.setParameter("userId", userId);
        query.setParameter("recipeId", recipeId);

        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public Boolean isRecipeDislikedById(long userId, long recipeId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(ld) FROM RecipeLikeDislike ld WHERE ld.user.id = :userId AND ld.recipe.recipeId = :recipeId AND ld.isLike = false",
                Long.class);
        query.setParameter("userId", userId);
        query.setParameter("recipeId", recipeId);

        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public void makeRecipeLiked(long userId, long recipeId) {
        removeRecipeRating(userId, recipeId);
        addRating(userId, recipeId, true);
    }


    @Override
    public void makeRecipeDisliked(long userId, long recipeId) {
        removeRecipeRating(userId, recipeId);
        addRating(userId, recipeId, false);
    }

    private void addRating(long userId, long recipeId, boolean isLike) {
        final RecipeLikeDislike recipeLikeDislike = new RecipeLikeDislike( em.getReference(Recipe.class, recipeId), em.getReference(User.class, userId),isLike);
        em.persist(recipeLikeDislike);
    }

    @Override
    public void removeRecipeRating(long userId, long recipeId) {
        Optional<RecipeLikeDislike> likeDislikeJpa = Optional.ofNullable(em.find(RecipeLikeDislike.class, new RecipeLikeDislike.LikeDislikeId(recipeId, userId)));
        likeDislikeJpa.ifPresent(recipeLikeDislike -> em.remove(recipeLikeDislike));
    }

}
