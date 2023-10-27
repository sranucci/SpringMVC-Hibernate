package ar.edu.itba.paw.models.ingredient;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecipeIngredientTransporter {

    private final String name;
    private final Long units;
    private final Float quantity;

}
