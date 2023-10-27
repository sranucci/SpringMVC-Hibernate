package ar.edu.itba.paw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum AvailableDifficultiesForSort {
    ALL("All", 0),
    EASY("Easy", 1),
    INTERMEDIATE("Intermediate", 2),
    HARD("Hard", 3);

    private static final Map<Long, AvailableDifficultiesForSort> map = new HashMap<>();

    static {
        for (AvailableDifficultiesForSort difficulty : AvailableDifficultiesForSort.values()) {
            map.put(difficulty.getDifficultyId(), difficulty);
        }
    }

    private final String difficultyName;
    private final long difficultyId;


}
