package ar.edu.itba.paw.models.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_ingredient")
public class Ingredient {

    @Id
    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @Column(name = "ingredient_name", nullable = false)
    private String name;
}
