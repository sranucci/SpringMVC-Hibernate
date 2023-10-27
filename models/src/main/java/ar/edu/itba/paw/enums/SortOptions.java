package ar.edu.itba.paw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortOptions {
    NONE("None", "None"),
    ALPHABETIC_ASC("title ASC", "A-Z"),
    ALPHABETIC_DESC("title DESC", "Z-A"),
    LIKES_ASC("likes ASC", "Least Liked"),
    LIKES_DESC("likes DESC", "Most Liked"),
    DIFFICULTY_ASC("difficulty_id ASC", "Least difficult"),
    DIFFICULTY_DESC("difficulty_id DESC", "Most difficult");

    private final String sortOption; // used in recipeDaoImpl sql query
    private final String sortOptionFrontEnd; // used to show to the user
}
