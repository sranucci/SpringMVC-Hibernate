package ar.edu.itba.paw.models.recipe;

import ar.edu.itba.paw.enums.AvailableDifficulties;
import ar.edu.itba.paw.models.RecipeLikeDislike;
import ar.edu.itba.paw.models.category.RecipeCategory;
import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.ingredient.RecipeIngredient;
import ar.edu.itba.paw.models.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_recipe_recipe_id_seq")
    @SequenceGenerator(sequenceName = "tbl_recipe_recipe_id_seq", name = "tbl_recipe_recipe_id_seq", allocationSize = 1)
    @Column(name="recipe_id")
    private Long recipeId;

    @Column(name = "language",nullable = false)
    private String language;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="is_private", nullable = false)
    private boolean isPrivate;

    @Column(name="total_minutes", nullable = false)
    private int totalMinutes;

    @Column(name="difficulty_id", nullable = false)
    private long difficulty;

    @Column(nullable = false)
    private int servings;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(length = 4096, nullable = false)
    private String instructions;



    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RecipeIngredient> ingredients;


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RecipeCategory> recipeCategories;


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RecipeImage> recipeImages;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeLikeDislike> likesAndDislikes;


    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Formula("(SELECT COUNT(*) FROM tbl_like_dislike l WHERE l.recipe_id = recipe_id AND l.is_like = true)")
    private long likes;

    @Formula("(SELECT COUNT(*) FROM tbl_like_dislike l WHERE l.recipe_id = recipe_id AND l.is_like = false)")
    private long dislikes;

    @Formula("(SELECT COUNT(*) FROM tbl_comment t WHERE t.recipe_id = recipe_id)")
    private long commentsCount;

    @Transient
    private long totalComments;

    @Transient
    private List<Comment> paginatedComments;

    public String[] getInstructions(){
        return instructions.split("#");
    }

    public void setSerializableInstructions(String[] instructions){
        this.instructions = Arrays.stream(instructions).reduce( (acuml,current) -> acuml + "#" + current).orElse("");
    }




    public Recipe(final String language, final String title, final String description, final User user, final boolean isPrivate, final int totalMinutes, final long difficulty, final int servings, final String instructions) {
        this.language = language;
        this.title = title;
        this.description = description;
        this.user = user;
        this.isPrivate = isPrivate;
        this.totalMinutes = totalMinutes;
        this.difficulty = difficulty;
        this.servings = servings;
        this.instructions = instructions;
        this.createdAt = Date.from(Instant.now());
    }


    public int getMinutes() {
        return totalMinutes % 60;
    }

    public int getHours() {
        return totalMinutes / 60;
    }

    public String getDifficultyString() {
        return AvailableDifficulties.getStringValue(difficulty);
    }


    public boolean getIsPrivate() {
        return isPrivate;
    }


    public RecipeImage getMainRecipeImage(){
        for ( RecipeImage img : recipeImages){
            if (img.isMainImage()){
                return img;
            }
        }
        return null;//could never happen
    }

    public long getMainImageId() {
        return getMainRecipeImage().getImageId();
    }


}
