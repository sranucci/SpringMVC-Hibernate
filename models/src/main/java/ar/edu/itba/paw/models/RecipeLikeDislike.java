package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_like_dislike")
public class RecipeLikeDislike implements Serializable {

    @EmbeddedId
    private LikeDislikeId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_like", nullable = false)
    private boolean isLike;

    public RecipeLikeDislike(Recipe recipe, User user, boolean like) {
        this.recipe = recipe;
        this.user = user;
        this.isLike = like;
        this.id = new LikeDislikeId(recipe.getRecipeId(), user.getId());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class LikeDislikeId implements Serializable {
        @Column(name = "recipe_id")
        private Long recipeId;

        @Column(name = "user_id")
        private Long userId;


        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ( ! (obj instanceof LikeDislikeId) ){
                return false;
            }
            LikeDislikeId other = (LikeDislikeId) obj;
            return Objects.equals(recipeId, other.recipeId) &&
                    Objects.equals(userId, other.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(recipeId, userId);
        }



    }
}
