package ar.edu.itba.paw.webapp.dtos;


import ar.edu.itba.paw.enums.AvailableDifficulties;
import ar.edu.itba.paw.models.category.Category;
import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.models.unit.Unit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeFormDto {

    private Gson gsonParser = new GsonBuilder().serializeNulls().create();
    private List<Ingredient> ingredients;
    private @Getter
    List<Unit> units;
    private @Getter List<Category> categories;
    private @Getter AvailableDifficulties[] difficulties;

    public RecipeFormDto(List<Ingredient> ingredients, List<Unit> units, List<Category> categories, AvailableDifficulties[] difficulties) {
        this.ingredients = ingredients;
        this.units = units;
        this.categories = categories;
        this.difficulties = difficulties;
    }

    //for materialize autocomplete, string MUST be key
    public String getAutocompleteJsonMap() {
        Map<String, Long> map = new HashMap<>();
        ingredients.forEach(ingredient -> map.put(ingredient.getName(), null));
        return gsonParser.toJson(map);
    }

    public String getUnitsJsonMap() {
        Map<Long, String> map = new HashMap<>();
        units.forEach(unit -> map.put(unit.getId(), unit.getUnitName()));
        return gsonParser.toJson(map);
    }


}
