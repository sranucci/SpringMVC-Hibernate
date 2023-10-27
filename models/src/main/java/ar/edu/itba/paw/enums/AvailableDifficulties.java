package ar.edu.itba.paw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum AvailableDifficulties {
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
    public static String getStringValue(long difficultyId) {
        AvailableDifficultiesForSort difficulty = map.get(difficultyId);
        if (difficulty == null) {
            throw new IllegalArgumentException("Invalid difficulty ID: " + difficultyId);
        }
        return difficulty.getDifficultyName();
    }
}
