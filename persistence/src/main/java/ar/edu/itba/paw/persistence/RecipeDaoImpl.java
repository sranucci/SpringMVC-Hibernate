package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.LanguagesForSort;
import ar.edu.itba.paw.enums.ShowRecipePages;
import ar.edu.itba.paw.enums.SortOptions;
import ar.edu.itba.paw.models.category.RecipeCategory;
import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.ingredient.RecipeIngredient;
import ar.edu.itba.paw.models.recipe.*;
import ar.edu.itba.paw.models.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RecipeDaoImpl implements ar.edu.itba.persistenceInterface.RecipeDao {
    @PersistenceContext
    private EntityManager em; //manejado x entityManager de webconfig

    private String serializeInstructions(String[] instructions) {
        return Arrays.stream(instructions).reduce((prevConcat, current) -> prevConcat + "#" + current).orElse("");//instructions never emptyor null
    }

    @Override
    public Recipe create(String language, String title, String description, long userId, boolean isPrivate, int totalMinutes, long difficultyId, int servings, String[] instructions) {
        String serializedInstructions = serializeInstructions(instructions);
        final Recipe recipe = new Recipe(language,title, description, em.find(User.class, userId), isPrivate, totalMinutes, difficultyId, servings, serializedInstructions);
        em.persist(recipe);
        return recipe;
    }

    @Override
    public Optional<Recipe> getRecipe(long recipeId) {
        return Optional.ofNullable(em.find(Recipe.class,recipeId));
    }

    @Override
    public Optional<Recipe> getRecipe(long recipeId, Optional<Integer> commentsToBring) {

        int results = commentsToBring.orElse(10);
        final Recipe r = em.find(Recipe.class,recipeId);
        if ( r == null)
            return Optional.empty();
        Query nativeQuery  = em.createNativeQuery("SELECT comment_id FROM tbl_comment WHERE recipe_id = :id ORDER BY created_at DESC ").setParameter("id",recipeId);
        nativeQuery.setMaxResults(results);
        nativeQuery.setFirstResult(0);

        int totalCommentCount = ( ( Number) em.createNativeQuery("SELECT count(*) FROM tbl_comment WHERE recipe_id = :id").setParameter("id",recipeId).getSingleResult()).intValue();

        final List<Long> idList = (List<Long>) nativeQuery.getResultList().stream().map( n -> ( (Number) n).longValue()).collect(Collectors.toList());
        if ( idList.size() == 0){
            r.setPaginatedComments(new ArrayList<>());
            r.setTotalComments(0);
            return Optional.of(r);
        }
        final TypedQuery<Comment> query = em.createQuery("select c FROM Comment c WHERE c.commentId IN :ids ORDER BY c.createdAt DESC ", Comment.class).setParameter("ids",idList);

        r.setPaginatedComments(query.getResultList());
        r.setTotalComments(totalCommentCount);
        return Optional.of(r);
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        em.remove(recipe);
    }

    @Override
    public boolean removeRecipe(long recipeId, long userId) {
        Optional<Recipe> recipe = Optional.ofNullable(em.find(Recipe.class, recipeId));
        if(recipe.isPresent()){
            em.remove(recipe.get());
            return true;
        }else{
            return false;
        }

    }



    @Override
    public List<Recipe> getRecipesByFilter(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients,
                                           List<Integer> categories, Optional<SortOptions> sort,
                                           Optional<String> searchQuery, ShowRecipePages pageToShow,
                                           Optional<Long> userId, Optional<Long> page, Optional<Integer> limit, Optional<LanguagesForSort> language) {

        Map<String, Object> parameters = new HashMap<>();
        List<String> whereClauses = new ArrayList<>();

        String sql = "SELECT r.recipe_id  FROM recipedatawithlikesview r ";


        sql = getSqlForFilter(difficulty, ingredients, categories, searchQuery, pageToShow, userId, language, parameters, whereClauses, sql);


        //sort
        if (sort.isPresent() && sort.get() != SortOptions.NONE) {
            SortOptions sortOptionValue = sort.get();
            sql += " ORDER BY " + sortOptionValue.getSortOption() + " ";
        } else {
            sql += " ORDER BY r.recipe_id DESC";
        }

        Query nativeQuery = em.createNativeQuery(sql);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            nativeQuery.setParameter(entry.getKey(),entry.getValue());
        }

        int lim = limit.orElse(9);
        nativeQuery.setMaxResults(lim);
        int pageNumber = page.map(Long::intValue).orElse(1);
        nativeQuery.setFirstResult(( pageNumber - 1) * lim );

        @SuppressWarnings("unchecked")
        final List<Long> idList = (List<Long>) nativeQuery.getResultList().stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        if (idList.isEmpty()) {
            return Collections.emptyList();
        }


        final TypedQuery<Recipe> recipeQuery;
        if(sort.isPresent() && sort.get() != SortOptions.NONE){
            recipeQuery = em.createQuery("from Recipe where recipeId IN :ids order by " + sort.get().getSortOption(),  Recipe.class);
        }else{
            recipeQuery = em.createQuery("from Recipe where recipeId IN :ids order by recipeId desc" , Recipe.class);
        }


        recipeQuery.setParameter("ids", idList);

        return recipeQuery.getResultList();


    }

    private String getSqlForFilter(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients, List<Integer> categories, Optional<String> searchQuery, ShowRecipePages pageToShow, Optional<Long> userId, Optional<LanguagesForSort> language, Map<String, Object> parameters, List<String> whereClauses, String sql) {
        //filter by page
        if (pageToShow == ShowRecipePages.DISCOVER) {
            whereClauses.add("r.is_private = false");
        } else if (pageToShow == ShowRecipePages.MY_RECIPES && userId.isPresent()) {
            whereClauses.add("r.user_id = " + userId.get());
        } else if (pageToShow == ShowRecipePages.SAVED && userId.isPresent()) {
            whereClauses.add("r.recipe_id IN (SELECT s.recipe_id FROM tbl_saved_by_user s WHERE s.user_id = " + userId.get() + ")");
        }

        //filter by ingredient
        if (ingredients.isPresent()) {
            String[] ingredientsList = Arrays.stream(ingredients.get().split("#"))
                    .map(keyword ->  InputCleaner.clean(keyword).toUpperCase() )
                    .toArray(String[]::new);

            String ingredientCondition = "r.recipe_id IN (SELECT ri.recipe_id FROM tbl_recipe_ingredient ri JOIN tbl_ingredient i ON ri.ingredient_id = i.ingredient_id WHERE UPPER(i.ingredient_name) IN (:ingredientNames) )";
            whereClauses.add(ingredientCondition);
            parameters.put("ingredientNames", Arrays.asList(ingredientsList));
        }

        //filter by categories
        if (categories.size() != 0) {
            whereClauses.add("r.recipe_id IN (SELECT rct.recipe_id FROM tbl_recipe_category rct JOIN tbl_category rc ON rct.category_id = rc.category_id WHERE rc.category_id IN (:categoryIds))");
            parameters.put("categoryIds", categories);
        }

        // Filter by SearchQuery
        if (searchQuery.isPresent()) {
            String[] titleList = Arrays.stream(searchQuery.get().split("\\s+"))
                    .map(keyword -> "%" + InputCleaner.clean(keyword).toUpperCase() + "%")
                    .toArray(String[]::new);

            for (int i = 0; i < titleList.length; i++) {
                String parameterName = "titleQuery" + i;
                whereClauses.add("UPPER(r.title) LIKE :" + parameterName);
                parameters.put(parameterName, titleList[i]);
            }
        }

        // Filter by difficulty
        if (difficulty.isPresent() && difficulty.get() != AvailableDifficultiesForSort.ALL) {
            whereClauses.add("r.difficulty_id = :difficultyId");
            parameters.put("difficultyId", difficulty.get().getDifficultyId());
        }

        // Filter by language
        if (language.isPresent() && language.get() != LanguagesForSort.ALL) {
            whereClauses.add("r.language = :languageCode");
            parameters.put("languageCode", language.get().getLanguageCode());
        }


        if (!whereClauses.isEmpty()) {
            sql += "WHERE " + String.join(" AND ", whereClauses);
        }
        return sql;
    }


    @Override
    public long getTotalNumberRecipesByFilterForPagination(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients,
                                                           List<Integer> categories, Optional<SortOptions> sort, Optional<String> searchQuery,
                                                           ShowRecipePages pageToShow, Optional<Long> userId, Optional<Long> page,
                                                           Optional<Integer> pageSize, Optional<LanguagesForSort> language) {

        Map<String, Object> parameters = new HashMap<>();
        List<String> whereClauses = new ArrayList<>();

        String sql = "SELECT COUNT(r.recipe_id)  FROM recipedatawithlikesview r ";


        sql = getSqlForFilter(difficulty, ingredients, categories, searchQuery, pageToShow, userId, language, parameters, whereClauses, sql);

        Query nativeQuery = em.createNativeQuery(sql);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            nativeQuery.setParameter(entry.getKey(), entry.getValue());
        }

        return ((Number) nativeQuery.getSingleResult()).longValue();
    }


    @Override
    public boolean updateRecipe(String language, String title, String description, boolean isPrivate, int totalMinutes, long difficultyId,
                                int servings, String[] instructions, List<RecipeIngredient> ings,
                                List<RecipeCategory> cats, List<RecipeImage> imgs, RecipeImage mainImage , Recipe recipe) {
        recipe.setLanguage(language);
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setPrivate(isPrivate);
        recipe.setTotalMinutes(totalMinutes);
        recipe.setDifficulty(difficultyId);
        recipe.setServings(servings);
        recipe.setSerializableInstructions(instructions);
        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(ings);
        recipe.getRecipeCategories().clear();
        recipe.getRecipeCategories().addAll(cats);

        List<RecipeImage> imageList = recipe.getRecipeImages();
        if ( mainImage == null && imgs != null) {
            RecipeImage newMainImage = recipe.getMainRecipeImage();
            imageList.clear();
            imageList.addAll(imgs);
            imageList.add(newMainImage);
        } else if ( mainImage != null && imgs == null){
            imageList.removeIf(RecipeImage::isMainImage);
            imageList.add(mainImage);
        } else if (mainImage != null) {
            //si ninguna de ellas es null
            imageList.clear();
            imageList.addAll(imgs);
            imageList.add(mainImage);
        }
        em.merge(recipe);
        return true;
    }

    @Override
    public List<Recipe> getRecipesForFeed(Long userId, Optional<Integer> page, Optional<Integer> pageSize) {

        String query = "SELECT r.recipe_id " +
                "FROM tbl_recipe r " +
                "JOIN tbl_user u ON r.user_id = u.user_id " +
                "JOIN tbl_follows f ON f.user_followed_id = u.user_id " +
                "WHERE f.user_id = :userId " +
                "AND r.is_private = false " +
                "ORDER BY r.created_at DESC";

        Query paginationQuery = em.createNativeQuery(query);
        paginationQuery.setParameter("userId", userId);
        paginationQuery.setMaxResults(pageSize.orElse(9));
        paginationQuery.setFirstResult((page.orElse(1) - 1 ) * pageSize.orElse(9));

        @SuppressWarnings("unchecked")
        final List<Long> idList = (List<Long>) paginationQuery.getResultList().stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());


        if(idList.isEmpty()){
            return new ArrayList<>();
        }

        final TypedQuery<Recipe> recipeQuery = em.createQuery("from Recipe WHERE recipeId in :ids order by createdAt desc", Recipe.class);
        recipeQuery.setParameter("ids", idList);

        return recipeQuery.getResultList();
    }

    @Override
    public long getNumberOfRecipesForFeed(Long userId) {
        String query = "SELECT COUNT(recipe_id)" +
                "FROM tbl_recipe r " +
                "JOIN tbl_user u ON r.user_id = u.user_id " +
                "JOIN tbl_follows f ON f.user_followed_id = u.user_id " +
                "WHERE f.user_id = :userId " +
                "AND r.is_private = false ";

        Query paginationQuery = em.createNativeQuery(query);
        paginationQuery.setParameter("userId", userId);
        return ((Number) paginationQuery.getSingleResult()).longValue();
    }

    @Override
    public List<Recipe> getRecipesForProfile(Long userId, Optional<Integer> page, Optional<Integer> pageSize) {
        String sql = "SELECT recipe_id " +
                "FROM tbl_recipe WHERE user_id = :id and is_private = false " +
                "ORDER BY created_at DESC";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", userId);

        int lim = pageSize.orElse(9);
        query.setMaxResults(lim);
        int pageNumber = page.orElse(1);
        query.setFirstResult(( pageNumber - 1) * lim );

        @SuppressWarnings("unchecked")
        final List<Long> idList = (List<Long>) query.getResultList().stream()
                .map(n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        if(idList.isEmpty()){
            return new ArrayList<>();
        }


        final TypedQuery<Recipe> recipeQuery = em.createQuery("from Recipe WHERE recipeId in :ids order by createdAt desc", Recipe.class);
        recipeQuery.setParameter("ids", idList);

        return recipeQuery.getResultList();
    }

    @Override
    public long getNumberOfRecipesForProfile(Long userId) {
        String sql = "SELECT COUNT(recipe_id) " +
                "FROM tbl_recipe WHERE user_id = :id and is_private = false";

        Query query = em.createNativeQuery(sql);
        query.setParameter("id", userId);
        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public List<Long> getRecipesLiked(Optional<Long> currentUserId) {
        String sql = "SELECT r.recipe_id " +
                "FROM tbl_recipe r JOIN tbl_like_dislike l ON r.recipe_id = l.recipe_id WHERE l.is_like = true and l.user_id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", currentUserId.get());
        List<Long> results = query.getResultList();
        return results;
    }

    @Override
    public List<Long> getRecipesDisliked(Optional<Long> currentUserId) {
        String sql = "SELECT r.recipe_id " +
                "FROM tbl_recipe r JOIN tbl_like_dislike l ON r.recipe_id = l.recipe_id WHERE l.is_like = false and l.user_id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", currentUserId.get());
        List<Long> results = query.getResultList();
        return results;
    }
}
