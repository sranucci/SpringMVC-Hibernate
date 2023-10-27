package ar.edu.itba.paw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum LanguagesForSort {
    ALL("All", "all"),
    ENGLISH("English", "en"),
    SPANISH("Spanish", "es");

    private final String languageName;
    private final String languageCode;


    private static final Map<String, LanguagesForSort> map = new HashMap<>();

    static {
        for (LanguagesForSort ln : LanguagesForSort.values()) {
            map.put(ln.languageCode, ln);
        }
    }
}