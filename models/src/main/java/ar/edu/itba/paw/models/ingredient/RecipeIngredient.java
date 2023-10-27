package ar.edu.itba.paw.models.ingredient;

import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.unit.Unit;
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
@Table(name = "tbl_recipe_ingredient")
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Column(name = "quantity", nullable = false)
    private float quantity;

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, Unit unit, float quantity) {
        this.id = new RecipeIngredientId(recipe.getRecipeId(), ingredient.getIngredientId());
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.unit = unit;
        this.quantity = quantity;
    }

    @Setter
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeIngredientId implements Serializable {

        @Column(name = "recipe_id")
        private Long recipeId;

        @Column(name = "ingredient_id")
        private Integer ingredientId;

    }
}