package ar.edu.itba.paw.models.recipe;

import ar.edu.itba.paw.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_saved_by_user")
public class SavedRecipe {

    @EmbeddedId
    private SavedRecipeId id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    public SavedRecipe(Recipe recipe, User user) {
        this.recipe = recipe;
        this.user = user;
        this.id = new SavedRecipeId(recipe.getRecipeId(), user.getId());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Embeddable
    public static class  SavedRecipeId implements Serializable {
        private Long recipeId;
        private Long userId;

    }

}

