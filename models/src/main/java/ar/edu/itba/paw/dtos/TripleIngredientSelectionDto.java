package ar.edu.itba.paw.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TripleIngredientSelectionDto {
    private String ingredient;
    private float quantity;
    private long unitId;
}
