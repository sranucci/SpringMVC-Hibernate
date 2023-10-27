package ar.edu.itba.paw.models.category;

import ar.edu.itba.paw.models.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_recipe_category")
public class RecipeCategory {

    @EmbeddedId
    private RecipeCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    public RecipeCategory(Recipe recipe, Category category) {
        this.id = new RecipeCategoryId(recipe.getRecipeId(), category.getCategoryId());
        this.recipe = recipe;
        this.category = category;
    }

    @Setter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecipeCategoryId implements Serializable {

        @Column(name = "recipe_id")
        private Long recipeId;

        @Column(name = "category_id")
        private Long categoryId;

    }
}


