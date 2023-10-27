package ar.edu.itba.paw.models.recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_recipe_photo")
public class RecipeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_recipe_photo_id_seq")
    @SequenceGenerator(sequenceName = "tbl_recipe_photo_id_seq", name = "tbl_recipe_photo_id_seq", allocationSize = 1)
    @Column(name="id")
    private Long imageId;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Column(name="photo_data", nullable = false)
    private byte[] image;

    @Column(name="is_primary_photo", nullable = false )
    private boolean mainImage;


    public RecipeImage(Recipe recipe, byte[] image, boolean mainImage) {
        this.recipe = recipe;
        this.image = image;
        this.mainImage = mainImage;
    }
}
