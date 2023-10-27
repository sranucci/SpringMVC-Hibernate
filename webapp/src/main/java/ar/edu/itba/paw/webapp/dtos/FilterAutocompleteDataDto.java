package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.SortOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ar.edu.itba.paw.enums.LanguagesForSort;
import lombok.Getter;

import java.util.Map;

@Getter
public class FilterAutocompleteDataDto {
    private final Map<String, Object> autocompleteMap;
    private final Map<Long, String> categoryMap;
    private final AvailableDifficultiesForSort[] difficultyOptions;
    private final SortOptions[] sortOptions; //las opciones de sort
    private final LanguagesForSort[] languages;
    private Gson gsonParser = new GsonBuilder().serializeNulls().create();

    public FilterAutocompleteDataDto(Map<String, Object> autocompleteMap, Map<Long, String> categoryMap) {
        this.autocompleteMap = autocompleteMap;
        this.categoryMap = categoryMap;
        this.difficultyOptions = AvailableDifficultiesForSort.values();
        this.sortOptions = SortOptions.values();
        this.languages = LanguagesForSort.values();
    }

    public String getAutocompleteJsonMap() {
        return gsonParser.toJson(autocompleteMap);
    }

}
